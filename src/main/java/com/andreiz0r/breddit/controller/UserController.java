package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.controller.message.UpdateUserRequest;
import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.UserService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.andreiz0r.breddit.utils.AppUtils.USER_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(USER_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class UserController extends AbstractRestController {
    private final UserService userService;
//todo: webflux
    @GetMapping
    public Response findAllUsers() {
        List<UserDTO> users = userService.findAll();
        return !users.isEmpty() ?
               successResponse(users) :
               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable final Integer id) {
        System.out.println("id: " + id);
        return userService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(User.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username={username}")
    public Response findByUsername(@PathVariable final String username) {
        return userService.findByUsername(username)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(User.class, "username", username), HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public Response updateUser(@PathVariable final Integer id, @RequestBody final UpdateUserRequest request){
     return userService.update(id, request)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(User.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Response deleteUserById(@PathVariable final Integer id) {
        return userService.deleteById(id) ?
               successResponse() :
               failureResponse(AppUtils.constructFailedDeleteMessage(User.class, id), HttpStatus.NOT_FOUND);
    }
}