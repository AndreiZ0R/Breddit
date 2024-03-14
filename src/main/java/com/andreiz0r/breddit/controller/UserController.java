package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.UserService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AppUtils.USERS_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class UserController extends AbstractRestController {
    private final UserService userService;

    @GetMapping
    public Response getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return !users.isEmpty() ?
               successResponse(users) :
               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);
    }
}