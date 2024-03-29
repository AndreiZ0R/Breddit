package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.dto.SubthreadDTO;
import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.model.Subthread;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.SubthreadService;
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

import static com.andreiz0r.breddit.utils.AppUtils.SUBTHREAD_CONTROLLER_ENDPOINT;


@RestController
@RequestMapping(SUBTHREAD_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class SubthreadController extends AbstractRestController{
    final SubthreadService subthreadService;

    @GetMapping
    public Response findAllSubthreads() {
        List<SubthreadDTO> subthreads = subthreadService.findAll();
        return !subthreads.isEmpty() ?
               successResponse(subthreads) :
               failureResponse(AppUtils.constructFailedToFetch(User.class), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Response getById(@PathVariable final Integer id) {
        return subthreadService.findById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(Subthread.class, "id", id), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable final Integer id){
        return subthreadService.deleteById(id)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructNotFoundMessage(Subthread.class, "id", id), HttpStatus.NOT_FOUND));
    }
}
