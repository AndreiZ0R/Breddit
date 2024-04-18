package com.andreiz0r.breddit.exception;

import com.andreiz0r.breddit.controller.AbstractRestController;
import com.andreiz0r.breddit.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler extends AbstractRestController {

    @ExceptionHandler({Exception.class, RuntimeException.class, ApiException.class})
    public Response handle(final Exception exception) {
        log.error(Arrays.toString(exception.getStackTrace()));
        return failureResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
