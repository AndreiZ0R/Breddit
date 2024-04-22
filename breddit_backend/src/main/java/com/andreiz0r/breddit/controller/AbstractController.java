package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.response.BasicResponse;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.utils.Topic;
import com.andreiz0r.breddit.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;

//TODO: maybe make this and services an abstract version for CRUDs
public class AbstractController {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendSuccessMessage(final Object body, final Topic topic) {
        simpMessagingTemplate.convertAndSend(topic.toString(), successResponse(body));
    }

    public void sendFailureMessage(final String errorMessage, final Topic topic) {
        simpMessagingTemplate.convertAndSend(topic.toString(), failureResponse(errorMessage));
    }

    public Response successResponse() {
        return response(HttpStatus.OK, AppUtils.SUCCESS);
    }

    public Response successResponse(final Object body) {
        return response(body, HttpStatus.OK, AppUtils.SUCCESS);
    }

    public Response failureResponse(final String errorMessage) {
        return failureResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Response failureResponse(final String errorMessage, final HttpStatus errorStatus) {
        return new Response.Builder()
                .withMessage(errorMessage)
                .withStatus(errorStatus)
                .build();
    }

    public Response response(final HttpStatus status, final String message) {
        return new Response.Builder()
                .withMessage(message)
                .withStatus(status)
                .build();
    }

    public Response response(final Object body, final HttpStatus status, final String message) {
        return new Response.Builder()
                .withMessage(message)
                .withStatus(status)
                .withBody(body)
                .build();
    }

    public Response response(final Object body, final String headerName, final String headerValue, final HttpStatus status, final String message) {
        return new Response.Builder()
                .withMessage(message)
                .withStatus(status)
                .withBody(body)
                .withHeader(headerName, headerValue)
                .build();
    }

    public Response response(final Object body, final HttpHeaders headers, final HttpStatus status, final String message) {
        return new Response.Builder()
                .withMessage(message)
                .withStatus(status)
                .withBody(body)
                .withHeaders(headers)
                .build();
    }

    public Response response(final Object body, final HttpStatus status, final String message, final Map<String, Object> extraFields) {
        return new Response.Builder()
                .withMessage(message)
                .withStatus(status)
                .withBody(body)
                .withMultipleFields(extraFields)
                .build();
    }

    @SneakyThrows
    public static void setServletResponse(final HttpServletResponse response, final String message, final int status) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        response.getWriter().println(objectMapper.writeValueAsString(basicResponse(message, HttpStatus.valueOf(status))));
    }

    public static BasicResponse basicResponse(final String message, final HttpStatus status) {
        return BasicResponse.builder()
                .message(message)
                .status(status)
                .build();
    }
}
