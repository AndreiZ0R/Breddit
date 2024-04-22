package com.andreiz0r.breddit.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(final List<String> stringList) {
        return Objects.nonNull(stringList) ? String.join(SPLIT_CHAR, stringList) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(final String string) {
        return Objects.nonNull(string) && string.length() > 1 ? Arrays.asList(string.split(SPLIT_CHAR)) : Collections.emptyList();
    }
}
