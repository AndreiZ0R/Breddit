package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.entity.Comment;
import com.andreiz0r.breddit.entity.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link com.andreiz0r.breddit.entity.Post}
 */
public record ViewPostDTO(
        Integer id,
        String title,
        String body,
        UserDTO author,
        Timestamp postedAt,
        List<CommentDTO> comments,
        Integer votes,
        Integer subthreadId,
        String subthreadName,
        List<String> imagesUrl
) implements Serializable {
}