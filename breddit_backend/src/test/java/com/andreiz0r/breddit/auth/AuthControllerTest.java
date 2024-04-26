package com.andreiz0r.breddit.auth;

import com.andreiz0r.breddit.auth.message.RegisterRequest;
import com.andreiz0r.breddit.auth.message.RegisterResponse;
import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.security.JwtService;
import com.andreiz0r.breddit.utils.AuthUtils;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.TestUtils;
import com.andreiz0r.breddit.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO:
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Test
    @SneakyThrows
    void shouldRegister() {
        // Given
        User user = UserUtils.createRandomUser();
        RegisterRequest request = AuthUtils.fromUser(user);
        String token = Randoms.alphabetic();

        RegisterResponse response = RegisterResponse.builder()
                .user(DTOMapper.mapUserToDTO(user))
                .token(token)
                .build();

        when(jwtService.generateToken(user)).thenReturn(token);
        when(authService.register(request)).thenReturn(response);

        // When & Then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.toJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(contains(token)));
    }
}
