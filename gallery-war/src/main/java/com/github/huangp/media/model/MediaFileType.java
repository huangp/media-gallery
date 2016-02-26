package com.github.huangp.media.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

public enum MediaFileType {
    all(), video("mp4", "mkv", "mov", "avi"), photo("jpg", "jpeg");

    private final List<String> extensions;

    MediaFileType(String... extensions) {
        this.extensions = ImmutableList.copyOf(extensions);
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
