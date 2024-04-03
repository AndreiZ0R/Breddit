package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.controller.message.CreatePostRequest;
import com.andreiz0r.breddit.controller.message.UpdatePostRequest;
import com.andreiz0r.breddit.dto.PostDTO;
import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.PostService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Response findAll() {
        List<PostDTO> posts = postService.findAll();
        return !posts.isEmpty() ?
               successResponse(posts) :
               failureResponse(AppUtils.constructFailedToFetch(Post.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable final Integer id) {
        return postService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(Post.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public Response createPost(@RequestBody final CreatePostRequest request) {
        return postService.store(request)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructFailedSaveMessage(Post.class)));
    }

    @PatchMapping("/{id}")
    public Response updatePost(@PathVariable final Integer id, @RequestBody final UpdatePostRequest request) {
        return postService.update(id, request)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(Post.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable final Integer id) {
        return postService.deleteById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(Post.class, "id", id), HttpStatus.NOT_FOUND));
    }
}
