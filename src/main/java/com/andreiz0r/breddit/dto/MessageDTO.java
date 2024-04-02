package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.User;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.andreiz0r.breddit.model.Message}
 */
public record MessageDTO(
        Integer id,
        UserDTO sender,
        UserDTO receiver,
        String content,
        Timestamp sentAt
) implements Serializable {
}