package com.github.huangp.media.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.constants.ServiceUrlConstants;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class PathUtil implements Serializable {
    private static String appRoot;
    private static String acctUri = KeycloakUriBuilder.fromUri("/auth").path(
            ServiceUrlConstants.ACCOUNT_SERVICE_PATH)
            .queryParam("referrer", "gallery-war").build("gallery")
            .toString();;
    private static String logoutUri;

    public static String getAppRoot(HttpServletRequest req) {
        if (appRoot != null) {
            return appRoot;
        }
        appRoot = String.format("%s://%s:%d/%s", req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath());
        return appRoot;
    }

    public static String getAccountUrl() {
        return acctUri;
    }

    public static String getLogoutUrl(String redirectUri) {
        if (logoutUri != null) {
            return logoutUri;
        }
        logoutUri = KeycloakUriBuilder.fromUri("http://localhost:8080/auth")
                .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
                .queryParam("redirect_uri", redirectUri)
                .build("gallery").toString();
        return logoutUri;
    }

}
