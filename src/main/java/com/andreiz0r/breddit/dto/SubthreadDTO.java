package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.Post;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.andreiz0r.breddit.model.Subthread}
 */
public record SubthreadDTO(
        Integer id,
        String name,
        String description,
        Integer membersCount,
        List<Post> posts
) implements Serializable {
}