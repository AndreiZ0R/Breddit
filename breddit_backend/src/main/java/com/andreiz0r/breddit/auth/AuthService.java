package com.andreiz0r.breddit.auth;

import com.andreiz0r.breddit.auth.message.AuthRequest;
import com.andreiz0r.breddit.auth.message.AuthResponse;
import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.auth.message.RegisterResponse;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.repository.UserRepository;
import com.andreiz0r.breddit.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
                .build();
    }

    public Optional<AuthResponse> login(final AuthRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        return userRepository.findByUsername(authenticationRequest.getUsername())
                .map(this::mapToAuthenticationResponse);
    }

    private AuthResponse mapToAuthenticationResponse(final User user) {
        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .user(DTOMapper.mapUserToDTO(user))
                .build();
    }
}
