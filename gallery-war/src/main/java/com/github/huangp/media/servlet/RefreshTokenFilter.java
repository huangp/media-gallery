package com.github.huangp.media.servlet;

import java.io.IOException;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.ServerRequest;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.servlet.ServletOAuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.AuthenticatedUser;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@WebFilter(value = "/app/*")
@ApplicationScoped
public class RefreshTokenFilter implements Filter {
    private static final Logger log =
            LoggerFactory.getLogger(RefreshTokenFilter.class);

    public static final String OAUTH_ERROR_ATTR = "oauthErrorAttr";

    @Inject
    private ServletOAuthClient oauthClient;

    @Inject
    private AuthenticatedUser userData;

    @Inject
    private ServletOAuthClient servletOAuthClient;

    private String appRoot;
    private String logoutUri;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws
            IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        Map<String, String[]> reqParams = request.getParameterMap();

        initUrls(request);

        if (reqParams.containsKey(OAuth2Constants.CODE)) {
            try {
                String accessToken = oauthClient.getBearerToken(request).getToken();

                KeycloakSecurityContext session = (KeycloakSecurityContext) req.getAttribute(KeycloakSecurityContext.class.getName());
                userData.setName(session.getIdToken().getName());
                userData.setUsername(session.getIdToken().getEmail());
//                return session.getIdToken();
                userData.setAccessToken(accessToken);
                log.info("======== authenticated user: {}", userData);
            } catch (ServerRequest.HttpFailure e) {
                throw new ServletException(e);
            }
        } else if (reqParams.containsKey(OAuth2Constants.ERROR)) {
            String oauthError = reqParams.get(OAuth2Constants.ERROR)[0];
            request.setAttribute(OAUTH_ERROR_ATTR, oauthError);
            log.error("OAuth error occured: {}", oauthError);
            // TODO throw exception on oauth error?
            response.getWriter().write(oauthError);
        }
        chain.doFilter(request, response);
    }

    private void initUrls(HttpServletRequest request) {
        if (appRoot == null) {
            synchronized (this) {
                if (appRoot == null) {
                    appRoot =
                            String.format("%s://%s:%d/%s", request.getScheme(),
                                    request.getServerName(),
                                    request.getServerPort(),
                                    request.getContextPath());
                    logoutUri = KeycloakUriBuilder.fromUri("/auth")
                            .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
                            .queryParam("redirect_uri", appRoot + "/app/")
                            .build("gallery").toString();
                }
            }
        }
    }

    @Produces
    @SessionScoped
    protected PathHolder getPathHolder() {
        PathHolder pathHolder = new PathHolder();
        pathHolder.setAppRoot(appRoot);
        pathHolder.setLogoutUri(logoutUri);
        return pathHolder;
    }

    @Override
    public void destroy() {
    }
}
