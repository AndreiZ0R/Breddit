package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(AppUtils.USERS_CONTROLLER_ENDPOINT)
@RequestMapping("/api/users")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class UserController
//        extends ConcreteMessageController
{
    private final UserService userService;

//    @GetMapping()
//    public Response getAllUsers() {
//        List<UserDTO> users = userService.getAllUsers();
//        return !users.isEmpty() ?
//               successResponse(users) :
//               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);


    @GetMapping
    void ok() {
    }
}