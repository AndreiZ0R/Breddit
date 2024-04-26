package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.entity.User;

public class AuthUtils {

    public static RegisterRequest fromUser(final User user) {
        return RegisterRequest.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .country(user.getCountry())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();
    }
}
