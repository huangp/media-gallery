package com.github.huangp.media.service;

import java.util.List;

import com.github.huangp.media.model.Media;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public interface MediaSearchService {

    // TODO need to change wildfly configuration to allow access to folder on local file system http://stackoverflow.com/questions/31545261/wildfly-image-and-http-access-to-show-image-with-websocket
    List<Media> getAllMedia();

    String getAllMediaAsJSON();

    void getOne(String id);
}
