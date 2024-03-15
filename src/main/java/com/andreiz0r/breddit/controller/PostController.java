package com.andreiz0r.breddit.controller;


import com.andreiz0r.breddit.dto.PostDTO;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.PostService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.andreiz0r.breddit.utils.AppUtils.POST_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(POST_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class PostController extends AbstractRestController {
    final PostService postService;

    @GetMapping
    public Response getAllPosts() {
        List<PostDTO> posts = postService.findAll();
        return !posts.isEmpty() ?
               successResponse(posts) :
               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);
    }
}
