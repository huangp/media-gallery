package com.github.huangp.media.service;

import java.util.List;

import com.github.huangp.media.model.Media;
import com.github.huangp.media.model.MediaFileType;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public interface MediaSearchService {

    List<Media> getAllMedia();

    String getAllMediaAsJSON();

    String getOneAsJSON(String id);

    Media getOne(String id);

    String search(String fromDate, String toDate, MediaFileType mediaType);
}
