package com.andreiz0r.breddit.auth;

import com.andreiz0r.breddit.auth.message.AuthRequest;
import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.controller.AbstractController;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.entity.UserSession;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.utils.AppUtils;
import com.andreiz0r.breddit.utils.AppUtils.ReturnMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.andreiz0r.breddit.utils.AppUtils.ControllerEndpoints.AUTHENTICATION_CONTROLLER_ENDPOINT;
import static com.andreiz0r.breddit.utils.AppUtils.SESSION_ID_HEADER;

//TODO: refresh token + account validation with email
@RestController
@ControllerAdvice
@RequestMapping(AUTHENTICATION_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@Slf4j
public class AuthController extends AbstractController {
    private final AuthService service;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response register(@RequestBody final RegisterRequest registerRequest) {
        return successResponse(service.register(registerRequest));
    }

    @PostMapping("/login")
    public Response authenticate(@RequestBody final AuthRequest authenticationRequest) {
        return service.login(authenticationRequest)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.UNAUTHORIZED, HttpStatus.UNAUTHORIZED));
    }

    @GetMapping("/logout")
    public Response logout(@RequestHeader(SESSION_ID_HEADER) final UUID sessionId) {
        return service.logout(sessionId)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(UserSession.class, "sessionId", sessionId), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/logout-all")
    public Response logoutFromAllDevices(@AuthenticationPrincipal final User user) {
        return service.logout(user)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(UserSession.class, "userId", user.getId()), HttpStatus.NOT_FOUND));
    }

    // TODO: get a token if sessionId is in DB
}