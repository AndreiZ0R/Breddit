package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link com.andreiz0r.breddit.model.Post}
 */
public record PostDTO(
        Integer id,
        String title,
        String body,
        UserDTO author,
        Timestamp postedAt,
        List<CommentDTO> comments,
        Integer votes,
        Integer subthreadId
) implements Serializable {
}