package com.andreiz0r.breddit.auth;

import com.andreiz0r.breddit.auth.message.AuthRequest;
import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.controller.AbstractRestController;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.andreiz0r.breddit.utils.AppUtils.AUTHENTICATION_CONTROLLER_ENDPOINT;

@RestController
@ControllerAdvice
@RequestMapping(AUTHENTICATION_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
public class AuthController extends AbstractRestController {
    private final AuthService service;

    @PostMapping(value = "/register", consumes = AppUtils.APPLICATION_JSON)
    public Response register(@RequestBody final RegisterRequest registerRequest) {
        return successResponse(service.register(registerRequest));
    }

    @PostMapping("/login")
    public Response authenticate(@RequestBody final AuthRequest authenticationRequest) {
        return service.login(authenticationRequest)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.UNAUTHORIZED, HttpStatus.UNAUTHORIZED));
    }
}