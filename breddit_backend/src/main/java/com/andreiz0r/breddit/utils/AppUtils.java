package com.andreiz0r.breddit.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AppUtils {

    public final static String PROFILE_IMAGE_PATH = "/profile/";
    public final static String POST_IMAGE_PATH = "/post/";
    public final static String MESSAGE = "message";
    public final static String STATUS = "status";
    public final static String PAYLOAD = "payload";
    public final static String SUCCESS = "Success";
    public final static String UNAUTHORIZED = "Unauthorized";
    public final static String BAD_TOKEN = "User must provide a valid token";
    public final static String ACCESS_DENIED = "Access denied, need higher privileges";
    public final static String JWT_START_STRING = "Bearer ";
    public final static String HOST = "http://localhost:";
    public final static String VITE_DEFAULT_HOST = "http://localhost:5173";
    public final static String API_DEFAULT_HOST = "http://localhost:8080";

    public final static int SECOND = 1000;
    public final static int MINUTE = 60 * SECOND;
    public final static int HOUR = 60 * MINUTE;
    public final static int DEFAULT_JWT_EXPIRY = 24 * HOUR;

    public final static int BYTE = 1;
    public final static int KILOBYTE = BYTE * 1024;
    public final static int MEGABYTE = KILOBYTE * 1024;

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

    public final static String[] WHITE_LIST_URLS = {
            "/socket/**",
            "/api/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/bus/v3/api-docs/**"};
    public final static List<String> JWT_FILTER_WHITELISTED_URLS = List.of("/socket", "/register", "/login", "/swagger-ui", "/v3/", "/bus");

    public interface ReturnMessages {
        String FILE_UPLOAD_STARTED = "File upload started";
        String FILE_UPLOAD_MISSING_POST = "Missing post id for file upload";

        static String fetchFailed(final Class<?> clazz) {
            return "Could not fetch " + clazz.getSimpleName() + "s";
        }

        static String notFound(Class<?> clazz, final String propertyName, final Object value) {
            return "Could not find " + clazz.getSimpleName() + " with " + propertyName + ": " + value;
        }

        static String failedToLoadResource(final String name) {
            return "Could not find resource with name " + name;
        }

        static String failedToLoadResources(final Integer id) {
            return "Could not find resources for id: " + id;
        }

        static String failedToSaveResource() {
            return "Could not save resource";
        }

        static String updateFailed(Class<?> clazz, final String propertyName, final Object value) {
            return "Update on " + clazz.getSimpleName() + " with " + propertyName + ": " + value + " failed.";
        }

        static String saveFailed(final Class<?> clazz) {
            return "Could not save " + clazz.getSimpleName();
        }

        static String deleteFailed(final Class<?> clazz, final Object id) {
            return "Failed to delete " + clazz.getSimpleName() + " with id(s): " + id;
        }

        static String usernameNotFound(final String username) {
            return "Username not found: " + username;
        }
    }

    public interface ControllerEndpoints {
        String USER_CONTROLLER_ENDPOINT = "api/users";
        String COMMENT_CONTROLLER_ENDPOINT = "/api/comments";
        String MESSAGE_CONTROLLER_ENDPOINT = "/api/messages";
        String IMAGE_CONTROLLER_ENDPOINT = "/api/images";
        String POST_CONTROLLER_ENDPOINT = "/api/posts";
        String SUBTHREAD_CONTROLLER_ENDPOINT = "/api/subthreads";
        String AUTHENTICATION_CONTROLLER_ENDPOINT = "/api/auth";
    }

    public interface Images {
        String FORMAT_JPG = "jpg";
        int DEFAULT_WIDTH = 640;
        int DEFAULT_HEIGHT = 480;
    }
}

