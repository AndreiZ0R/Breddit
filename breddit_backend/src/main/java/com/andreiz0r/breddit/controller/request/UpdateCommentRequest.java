package com.andreiz0r.breddit.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCommentRequest {
    @JsonProperty
    private String body;
    @JsonProperty
    private Integer votes;
}
