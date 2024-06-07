package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.dto.UserSessionDTO;
import com.andreiz0r.breddit.entity.UserSession;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.UserSessionService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.andreiz0r.breddit.utils.AppUtils.ControllerEndpoints.USER_SESSION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_SESSION_ENDPOINT)
@Slf4j
public class UserSessionController extends AbstractController {
    private final UserSessionService userSessionService;

    @GetMapping
    public Response findAll() {
        List<UserSessionDTO> usersSession = userSessionService.findAll();
        return !usersSession.isEmpty() ?
               successResponse(usersSession) :
               failureResponse(AppUtils.ReturnMessages.notFound(UserSession.class), HttpStatus.NOT_FOUND);
    }
}