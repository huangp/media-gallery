package com.github.huangp.service.impl;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.huangp.media.model.Media;

/**
 * @author Patrick Huang <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class ObjectMapperTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void canUnmarshall() throws IOException {
        Media media = objectMapper.readValue(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("data.json"), Media.class);

        System.out.println(media);
    }
}
