package com.github.huangp.media.servlet;

import java.io.Serializable;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class PathHolder implements Serializable {

    private String appRoot;
    private String logoutUri;

    public String getAppRoot() {
        return appRoot;
    }

    public String getLogoutUri() {
        return logoutUri;
    }

    public void setAppRoot(String appRoot) {
        this.appRoot = appRoot;
    }

    public void setLogoutUri(String logoutUri) {
        this.logoutUri = logoutUri;
    }
}
