package com.github.huangp.media.servlet;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.servlet.ServletOAuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.AuthenticatedUser;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@WebServlet(name = "signInServlet", urlPatterns = "/sign_in")
public class SignInServlet extends HttpServlet {
    private static final Logger log =
            LoggerFactory.getLogger(SignInServlet.class);

    @Inject
    private ServletOAuthClient servletOAuthClient;

    @Inject
    private AuthenticatedUser userData;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getUserPrincipal() == null) {
            log.debug("========= redirect to log in ");
            servletOAuthClient.redirectRelative("/app/", req, resp);
        } else {
            log.info("===== you are already logged in");
            resp.sendRedirect("/app");
        }
    }
}
