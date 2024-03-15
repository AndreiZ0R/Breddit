package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.controller.message.UpdateUserRequest;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(DTOMapper::mapUserToDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(final Integer id) {
        return userRepository.findById(id).map(DTOMapper::mapUserToDTO);
    }

    public Optional<UserDTO> findByUsername(final String username) {
        return userRepository.findByUsername(username).map(DTOMapper::mapUserToDTO);
    }

    public Optional<UserDTO> update(final Integer id, final UpdateUserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    try {
                        objectMapper.updateValue(user, request);
                    } catch (JsonMappingException e) {
                        log.warn("Update on user with id={}failed", user.getId());
                    }
                    userRepository.save(user);
                    return DTOMapper.mapUserToDTO(user);
                });
    }

    public boolean deleteById(final Integer id) {
        return userRepository.deleteUserById(id) == 1;
    }
}
