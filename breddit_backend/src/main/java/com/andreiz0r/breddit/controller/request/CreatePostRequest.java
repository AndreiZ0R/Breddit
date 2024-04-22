package com.andreiz0r.breddit.controller.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CreatePostRequest {
    private String title;
    private String body;
    private Integer authorId;
    private Integer subthreadId;
    private List<String> imagesUrl;
}
