package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.dto.CommentDTO;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.CommentService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.andreiz0r.breddit.utils.AppUtils.COMMENT_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(COMMENT_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class CommentController extends AbstractRestController{
    final CommentService commentService;

    @GetMapping
    public Response getAllComments() {
        List<CommentDTO> users = commentService.findAll();
        return !users.isEmpty() ?
               successResponse(users) :
               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response getById(@PathVariable final Integer id) {
        return commentService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(User.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Response deleteCommentById(@PathVariable final Integer id) {
        return commentService.deleteById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructFailedDeleteMessage(User.class, id), HttpStatus.NOT_FOUND));
    }
}
