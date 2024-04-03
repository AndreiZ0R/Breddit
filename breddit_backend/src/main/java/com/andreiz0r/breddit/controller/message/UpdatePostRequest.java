package com.andreiz0r.breddit.controller.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePostRequest {
    @JsonProperty
    private String title;
    @JsonProperty
    private String body;
    @JsonProperty
    private Integer votes;
}
