package com.andreiz0r.breddit.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.andreiz0r.breddit.entity.Subthread}
 */
public record SubthreadDTO(
        Integer id,
        String name,
        String description,
        Integer membersCount,
        List<PostDTO> posts
) implements Serializable {
}