package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.User;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.andreiz0r.breddit.model.Comment}
 */
public record CommentDTO(
        Integer id,
        User author,
        String body,
        Timestamp postedAt,
        Integer votes,
        Integer postId,
        Integer parentId
) implements Serializable {
}