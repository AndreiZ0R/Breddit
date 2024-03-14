package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.User;

//TODO: role
public class DTOMapper {
    public static UserDTO mapUserToDTO(final User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry(),
                user.getCreatedAt(),
                user.getBirthDate());
//                user.getUserRole());
    }
}
