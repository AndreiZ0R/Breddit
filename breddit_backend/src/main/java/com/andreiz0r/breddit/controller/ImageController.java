package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.ImageService;
import com.andreiz0r.breddit.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.andreiz0r.breddit.utils.AppUtils.IMAGE_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(IMAGE_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ImageController extends AbstractRestController {

    private final ImageService imageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping(value = "/profile/{resourceName}", produces = MediaType.IMAGE_PNG_VALUE)
    public Response findProfileImage(@PathVariable final String resourceName) {
        return imageService.findImage(resourceName)
                .map(this::successResponse)
                .orElse(failureResponse(AppUtils.constructFailedToLoadResourceMessage(resourceName)));
    }

    //TODO:
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response saveProfileImage(@RequestBody final MultipartFile image) {
        var res = imageService.saveImage(image);

        res.thenAccept(fileName -> simpMessagingTemplate.convertAndSend(
                "/topic/messages",
                this.successResponse("File " + fileName + " upload is completed!")));

        return this.successResponse("File upload started");
    }

}
