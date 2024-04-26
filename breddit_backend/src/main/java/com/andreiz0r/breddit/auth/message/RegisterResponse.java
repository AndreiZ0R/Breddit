package com.andreiz0r.breddit.auth.message;

import com.andreiz0r.breddit.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private UserDTO user;
    private String token;
    private UUID sessionId;
}
