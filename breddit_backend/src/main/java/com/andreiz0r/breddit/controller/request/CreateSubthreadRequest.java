package com.andreiz0r.breddit.controller.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateSubthreadRequest {
    private String name;
    private String description;
}
