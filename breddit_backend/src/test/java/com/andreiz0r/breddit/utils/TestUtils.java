package com.andreiz0r.breddit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);

    public static Map<String, Object> createResponseBody(final Object payload, final HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(AppUtils.PAYLOAD, payload);
        responseBody.put(AppUtils.STATUS, status);

        return responseBody;
    }

    public static Map<String, Object> createResponseBody(final Object payload, final HttpStatus status, final String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(AppUtils.PAYLOAD, payload);
        responseBody.put(AppUtils.STATUS, status);
        responseBody.put(AppUtils.MESSAGE, message);

        return responseBody;
    }

    public static Object createResponseHeaders(final HttpHeaders headers) {
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public static String buildTestUrl(final String endPoint, final int port) {
        return AppUtils.HOST + port + endPoint;
    }

    public static String toJsonString(final Object toJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = "";
        try {
            value = objectMapper.writeValueAsString(toJson);
        } catch (JsonProcessingException e) {
            LOG.error(e::getMessage);
        }

        return value;
    }

    public static String toJsonString(final String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(AppUtils.MESSAGE, message);

        ObjectMapper objectMapper = new ObjectMapper();
        String value = "";
        try {
            value = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            LOG.error(e::getMessage);
        }

        return value;
    }

    public static String toJsonString(final Object body, final String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put(AppUtils.PAYLOAD, body);
        responseBody.put(AppUtils.MESSAGE, message);

        ObjectMapper objectMapper = new ObjectMapper();
        String value = "";
        try {
            value = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            LOG.error(e::getMessage);
        }

        return value;
    }
}
