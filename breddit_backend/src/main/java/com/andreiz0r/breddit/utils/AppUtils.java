package com.andreiz0r.breddit.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AppUtils {
    public static final String TEST_CONTROLLER_ENDPOINT = "api/test";
    public static final String USER_CONTROLLER_ENDPOINT = "api/users";
    public static final String COMMENT_CONTROLLER_ENDPOINT = "/api/comments";
    public static final String MESSAGE_CONTROLLER_ENDPOINT = "/api/messages";
    public static final String IMAGE_CONTROLLER_ENDPOINT = "/api/images";
    public static final String POST_CONTROLLER_ENDPOINT = "/api/posts";
    public static final String SUBTHREAD_CONTROLLER_ENDPOINT = "/api/subthreads";
    public static final String AUTHENTICATION_CONTROLLER_ENDPOINT = "/api/auth";
    public static final String APPLICATION_JSON = "application/json";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String PAYLOAD = "payload";
    public static final String SUCCESS = "Success";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String BAD_TOKEN = "User must provide a valid token";
    public static final String ACCESS_DENIED = "Access denied, need higher privileges";
    public static final String JWT_START_STRING = "Bearer ";
    public static final String HOST = "http://localhost:";
    public final static String VITE_DEFAULT_HOST = "http://localhost:5173";
    public final static String API_DEFAULT_HOST = "http://localhost:8080";


    public final static int SECOND = 1000;
    public final static int MINUTE = 60 * SECOND;
    public final static int HOUR = 60 * MINUTE;
    public final static int DEFAULT_JWT_EXPIRY = 24 * HOUR;

    public final static String[] WHITE_LIST_URLS = {
            "/socket/**",
            "/api/images/**",
            "/api/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/bus/v3/api-docs/**"};
    public final static List<String> JWT_FILTER_WHITELISTED_URLS = List.of("/socket","/images", "/register", "/login", "/swagger-ui", "/v3/", "/bus");

    public static String constructFailedToFetch(final Class<?> clazz) {
        return "Could not fetch " + clazz.getSimpleName() + "s";
    }

    public static String constructNotFoundMessage(Class<?> clazz, final String propertyName, final Object value) {
        return "Could not find " + clazz.getSimpleName() + " with " + propertyName + ": " + value;
    }

    public static String constructFailedToLoadResourceMessage(final String name) {
        return "Could not find resource with name " + name;
    }

    public static String constructUpdateFailedMessage(Class<?> clazz, final String propertyName, final Object value) {
        return "Update on " + clazz.getSimpleName() + " with " + propertyName + ": " + value + " failed.";
    }

    public static String constructFailedSaveMessage(final Class<?> clazz) {
        return "Could not save " + clazz.getSimpleName();
    }

    public static String constructFailedDeleteMessage(final Class<?> clazz, final Object id) {
        return "Failed to delete " + clazz.getSimpleName() + " with id(s): " + id;
    }

    public static String usernameNotFound(final String username) {
        return "Username not found: " + username;
    }

    public static Date now() {
        return new Date();
    }

    public static Timestamp timestampNow() {
        return Timestamp.from(Instant.now());
    }

    public static java.sql.Date sqlDateNow() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static Date nowWithDelay(final int delay) {
        return new Date(System.currentTimeMillis() + delay);
    }
}

