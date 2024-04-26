package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.response.Response;
import com.andreiz0r.breddit.service.ImageService;
import com.andreiz0r.breddit.service.PostService;
import com.andreiz0r.breddit.utils.AppUtils.ReturnMessages;
import com.andreiz0r.breddit.utils.ImageType;
import com.andreiz0r.breddit.utils.ImageTypeConverter;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.andreiz0r.breddit.utils.AppUtils.ControllerEndpoints.IMAGE_CONTROLLER_ENDPOINT;

@RestController
@RequestMapping(IMAGE_CONTROLLER_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ImageController extends AbstractController {

    private final ImageService imageService;
    private final PostService postService;
    private final ImageTypeConverter imageTypeConverter;

    @GetMapping(value = "/profile/{username}")
    public Response findProfileImage(@PathVariable final String username) {
        return imageService.findImage(username, ImageType.PROFILE)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.failedToLoadResource(username)));
    }

    @GetMapping(value = "/download", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> downloadResource(@RequestParam("path") final String resourcePath) {
        return ResponseEntity.of(imageService.findImage(resourcePath));
    }

    @GetMapping(value = "/post/{postId}")
    public Response findPostImages(@PathVariable final Integer postId) {
        return imageService.findImages(postId)
                .map(this::successResponse)
                .orElse(failureResponse(ReturnMessages.failedToLoadResources(postId)));
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response uploadPicture(@RequestBody final MultipartFile image,
                                  @RequestParam final String type,
                                  @RequestParam final Optional<Integer> postId,
                                  @AuthenticationPrincipal final User user) {

        ImageType imageType = imageTypeConverter.convert(type);
        if (imageType == ImageType.POST && postId.isEmpty()) {
            return failureResponse(ReturnMessages.FILE_UPLOAD_MISSING_POST, HttpStatus.BAD_REQUEST);
        }
        //TODO: topic maybe notifications, also create a Notification class for this
        imageService.saveImage(image, imageType == ImageType.POST ? Randoms.alphabetic(20) : user.getUsername(), imageType)
                .thenAccept(filenameOptional -> filenameOptional.ifPresentOrElse(
                        filename -> {
                            sendSuccessMessage(filename, Topic.NOTIFICATIONS);
                            postId.ifPresent(id -> postService.addImageUrl(id, filename));
                        },
                        () -> sendFailureMessage(ReturnMessages.failedToSaveResource(), Topic.MESSAGES)
                ));
        return successResponse(ReturnMessages.FILE_UPLOAD_STARTED);
    }
    //TODO: keep in mind for frontend, design pages to that they can be shareable(aka get from location).
    //TODO: on post delete, also delete images from it!
}
