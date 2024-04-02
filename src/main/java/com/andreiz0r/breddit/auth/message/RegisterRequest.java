package com.andreiz0r.breddit.auth.message;

import com.andreiz0r.breddit.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String country;
    private Date birthDate;
    private UserRole role;
}
