package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.controller.request.CreateSubthreadRequest;
import com.andreiz0r.breddit.controller.request.UpdateSubthreadRequest;
import com.andreiz0r.breddit.dto.SubthreadDTO;
import com.andreiz0r.breddit.model.Subthread;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.SubthreadService;
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

import static com.andreiz0r.breddit.utils.AppUtils.ControllerEndpoints.SUBTHREAD_CONTROLLER_ENDPOINT;


@RestController
@RequestMapping(SUBTHREAD_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class SubthreadController extends AbstractController {
    final SubthreadService subthreadService;

    @GetMapping
    public Response findAll() {
        List<SubthreadDTO> subthreads = subthreadService.findAll();
        return !subthreads.isEmpty() ?
               successResponse(subthreads) :
               failureResponse(ReturnMessages.fetchFailed(User.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable final Integer id) {
        return subthreadService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(Subthread.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public Response createSubthread(@RequestBody final CreateSubthreadRequest request) {
        return subthreadService.store(request)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.saveFailed(User.class), HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public Response updateSubthread(@PathVariable final Integer id, @RequestBody final UpdateSubthreadRequest request) {
        return subthreadService.update(id, request)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(User.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable final Integer id) {
        return subthreadService.deleteById(id)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.notFound(Subthread.class, "id", id), HttpStatus.NOT_FOUND));
    }
}
