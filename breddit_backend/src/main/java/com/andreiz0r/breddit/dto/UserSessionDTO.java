package com.andreiz0r.breddit.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.andreiz0r.breddit.entity.UserSession}
 */
public record UserSessionDTO(UUID sessionId, UserDTO user) implements Serializable {
}