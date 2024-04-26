package com.andreiz0r.breddit.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.andreiz0r.breddit.entity.Comment}
 */
public record CommentDTO(
        Integer id,
        UserDTO author,
        String body,
        Timestamp postedAt,
        Integer votes,
        Integer postId,
        Integer parentId
) implements Serializable {
}