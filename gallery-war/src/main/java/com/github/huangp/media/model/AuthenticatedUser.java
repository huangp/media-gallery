package com.github.huangp.media.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

import com.google.common.base.MoreObjects;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@SessionScoped
public class AuthenticatedUser implements Serializable {
    private String username;
    private String name;
    private String accessToken;

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("name", name)
                .add("accessToken", accessToken)
                .toString();
    }
}
