package com.andreiz0r.breddit.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public record UserDTO(
        Integer id,
        String username,
        String email,
        String country,
        Timestamp createdAt,
        Date birthDate
//        UserRole role
) implements Serializable {
}