package com.andreiz0r.breddit.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    @JsonProperty
    private List<String> imagesUrl;
}
