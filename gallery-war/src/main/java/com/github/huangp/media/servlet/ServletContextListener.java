package com.github.huangp.media.servlet;

import java.io.InputStream;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.keycloak.servlet.ServletOAuthClient;
import org.keycloak.servlet.ServletOAuthClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@WebListener
public class ServletContextListener implements
        javax.servlet.ServletContextListener {
    private static final Logger log =
            LoggerFactory.getLogger(ServletContextListener.class);
    @Inject
    private ServletOAuthClient oauthClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        InputStream is = context.getResourceAsStream("/WEB-INF/keycloak.json");

        ServletOAuthClientBuilder.build(is, oauthClient);
        log.info("OAuth client configured and started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        oauthClient.stop();
        log.info("OAuth client stopped");
    }
}
