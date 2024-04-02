package com.andreiz0r.breddit.controller.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreatePostRequest {
    private String title;
    private String body;
    private Integer authorId;
    private Integer subthreadId;
}
