package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.UserSessionDTO;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.entity.UserSession;
import com.andreiz0r.breddit.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO: delete by user id
@Service
@RequiredArgsConstructor
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;

    public List<UserSessionDTO> findAll() {
        return userSessionRepository.findAll().stream().map(DTOMapper::mapUserSessionToDTO).toList();
    }

//    public Optional<UserSessionDTO> store(final UserSession userSession){
//
//    }

    public Optional<UUID> storeSession(final User user) {
        return Optional.of(userSessionRepository.save(UserSession.builder().user(user).build()).getSessionId());
    }
}
