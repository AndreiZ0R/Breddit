package com.andreiz0r.breddit.controller.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateSubthreadRequest {
    private String name;
    private String description;
}
