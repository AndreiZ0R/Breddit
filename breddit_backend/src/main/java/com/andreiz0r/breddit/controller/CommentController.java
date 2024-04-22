package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.controller.request.CreateCommentRequest;
import com.andreiz0r.breddit.controller.request.UpdateCommentRequest;
import com.andreiz0r.breddit.dto.CommentDTO;
import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.CommentService;
import com.andreiz0r.breddit.utils.AppUtils.ReturnMessages;
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

import static com.andreiz0r.breddit.utils.AppUtils.ControllerEndpoints.COMMENT_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(COMMENT_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class CommentController extends AbstractController {
    final CommentService commentService;

    @GetMapping
    public Response findAll() {
        List<CommentDTO> comments = commentService.findAll();
        return !comments.isEmpty() ?
               successResponse(comments) :
               failureResponse(ReturnMessages.fetchFailed(Comment.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable final Integer id) {
        return commentService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(Comment.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public Response createComment(@RequestBody final CreateCommentRequest request) {
        return commentService.store(request)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.saveFailed(Comment.class)));
    }

    @PatchMapping("/{id}")
    public Response updateComment(@PathVariable final Integer id, @RequestBody final UpdateCommentRequest request) {
        return commentService.update(id, request)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(Comment.class, "id", id)));
    }

    @DeleteMapping("/{id}")
    public Response deleteCommentById(@PathVariable final Integer id) {
        return commentService.deleteById(id)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.deleteFailed(Comment.class, id), HttpStatus.NOT_FOUND));
    }
}
