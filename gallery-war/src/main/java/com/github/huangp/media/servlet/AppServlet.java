package com.github.huangp.media.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.AuthenticatedUser;
import com.github.huangp.media.util.PathUtil;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */

@WebServlet(name = "AppServlet", urlPatterns = "/app/*")
public class AppServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AppServlet.class);

    @Inject
    private AuthenticatedUser user;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws
            ServletException, IOException {

        resp.setContentType("text/html");

        KeycloakSecurityContext session = (KeycloakSecurityContext) req
                .getAttribute(KeycloakSecurityContext.class.getName());
        if (session != null) {
            user.setName(session.getIdToken().getName());
            user.setUsername(session.getIdToken().getEmail());
            Set<String> roles = session.getToken().getRealmAccess().getRoles();
            user.setRoles(roles);
        }

        KeycloakPrincipal principal = (KeycloakPrincipal) req.getUserPrincipal();

        log.debug("======== principal: {}, authenticated user: {}", principal,
                user);

        PrintWriter writer = resp.getWriter();
        String name =
                user.getName() == null ? "Anonymous" : user.getName();
        writer.println("hello " + name);
        String logoutUri = PathUtil.getLogoutUrl(PathUtil.getAppRoot(req));
        if (principal == null) {
            writer.println("<a href='/sign_in'>sign_in</a>");
        } else {
            writer.println("<a href='" + logoutUri + "'>logout</a>");
            writer.println("<a href='" + PathUtil.getAccountUrl() + "'>account</a>");
        }
    }

}
