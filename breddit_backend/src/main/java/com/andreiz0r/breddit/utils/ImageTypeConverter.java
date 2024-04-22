package com.andreiz0r.breddit.utils;

import org.springframework.stereotype.Component;

@Component
public class ImageTypeConverter {
    public ImageType convert(final String value) {
        return ImageType.valueOf(value.toUpperCase());
    }
}
