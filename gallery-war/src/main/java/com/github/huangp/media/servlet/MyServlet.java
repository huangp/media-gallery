package com.github.huangp.media.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.servlet.ServletOAuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.AuthenticatedUser;
import com.github.huangp.media.security.LogoutURI;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */

@WebServlet(name = "MyServlet", urlPatterns = "/app/*")
@ApplicationScoped
public class MyServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MyServlet.class);

    @Inject
    private ServletOAuthClient servletOAuthClient;
    @Inject
    private PathHolder pathHolder;

    @Inject
    private AuthenticatedUser userData;

    private String acctUri = KeycloakUriBuilder.fromUri("/auth").path(
            ServiceUrlConstants.ACCOUNT_SERVICE_PATH)
            .queryParam("referrer", "gallery-war").build("gallery").toString();

    @Inject
    private AuthenticatedUser authenticatedUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {

        if (userData.getAccessToken() == null) {
            log.info("========= redirect to log in ");
            servletOAuthClient.redirectRelative(req.getRequestURI(), req, resp);
        } else {
            PrintWriter writer = resp.getWriter();
            writer.write("hello from here");
            writer.println("<a href='" + pathHolder.getLogoutUri() + "'>logout</a>");
            writer.println("<a href='" + acctUri + "'>account</a>");
        }

    }
}
