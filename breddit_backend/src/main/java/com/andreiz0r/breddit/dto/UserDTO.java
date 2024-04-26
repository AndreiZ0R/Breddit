package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.entity.UserRole;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public record UserDTO(
        Integer id,
        String username,
        String email,
        String country,
        Timestamp createdAt,
        Date birthDate,
        UserRole role
) implements Serializable {
}