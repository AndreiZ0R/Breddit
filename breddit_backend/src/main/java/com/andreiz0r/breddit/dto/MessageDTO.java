package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.entity.ChatMessage;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link ChatMessage}
 */
public record MessageDTO(
        Integer id,
        UserDTO sender,
        UserDTO receiver,
        String content,
        Timestamp sentAt
) implements Serializable {
}