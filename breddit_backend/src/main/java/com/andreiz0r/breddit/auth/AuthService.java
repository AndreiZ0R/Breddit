package com.andreiz0r.breddit.auth;

import com.andreiz0r.breddit.auth.message.AuthRequest;
import com.andreiz0r.breddit.auth.message.AuthResponse;
import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.auth.message.RegisterResponse;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.UserSessionDTO;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.entity.UserSession;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.repository.UserSessionRepository;
import com.andreiz0r.breddit.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserSessionRepository userSessionRepository;

    public RegisterResponse register(final RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .country(registerRequest.getCountry())
                .birthDate(registerRequest.getBirthDate())
                .role(registerRequest.getRole())
                .build();


        return RegisterResponse.builder()
                .user(DTOMapper.mapUserToDTO(userRepository.save(user)))
                .token(jwtService.generateToken(user))
                .sessionId(userSessionRepository.save(new UserSession(user)).getSessionId())
                .build();
    }

    public Optional<AuthResponse> login(final AuthRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        return userRepository.findByUsername(authenticationRequest.getUsername())
                .map(user -> Pair.of(user, userSessionRepository.save(new UserSession(user)).getSessionId()))
                .map(this::mapToAuthenticationResponse);
    }

    public Optional<UserSessionDTO> logout(final UUID sessionId) {
        return userSessionRepository.findById(sessionId)
                .filter(session -> userSessionRepository.deleteSessionBySessionId(sessionId) != 0)
                .map(DTOMapper::mapUserSessionToDTO);
    }

    public Optional<UserSessionDTO> logout(final User user) {
        return userSessionRepository.findByUserId(user.getId())
                .filter(session -> userSessionRepository.deleteSessionByUserId(user.getId()) != 0)
                .map(DTOMapper::mapUserSessionToDTO);
    }

    private AuthResponse mapToAuthenticationResponse(final Pair<User, UUID> pair) {
        return AuthResponse.builder()
                .token(jwtService.generateToken(pair.getLeft()))
                .user(DTOMapper.mapUserToDTO(pair.getLeft()))
                .sessionId(pair.getRight())
                .build();
    }
}
