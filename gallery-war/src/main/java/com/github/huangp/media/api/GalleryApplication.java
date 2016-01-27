package com.github.huangp.media.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@ApplicationPath("api")
public class GalleryApplication extends ResourceConfig {

    public GalleryApplication() {
        packages(this.getClass().getPackage().getName(),
                "com.fasterxml.jackson.jaxrs.json");
    }
}
