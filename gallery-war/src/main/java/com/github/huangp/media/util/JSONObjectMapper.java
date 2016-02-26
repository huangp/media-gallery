package com.github.huangp.media.util;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@ApplicationScoped
public class JSONObjectMapper {
    private ObjectMapper objectMapper = new ObjectMapper();

    public <T> T fromJSON(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
