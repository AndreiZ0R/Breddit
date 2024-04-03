package com.andreiz0r.breddit.controller.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateCommentRequest {
    private Integer authorId;
    private String body;
    private Integer postId;
    private Integer parentId;
}
