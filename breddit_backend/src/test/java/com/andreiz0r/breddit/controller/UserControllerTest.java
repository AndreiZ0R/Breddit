package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.dto.DTOMapper;
import com.andreiz0r.breddit.dto.UserDTO;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.security.JwtService;
import com.andreiz0r.breddit.service.UserService;
import com.andreiz0r.breddit.utils.AppUtils;
import com.andreiz0r.breddit.utils.TestUtils;
import com.andreiz0r.breddit.utils.UserUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO:

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = UserController.class)
//@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;


    @SneakyThrows
    @Test
    @WithMockUser(roles = "User")
    void findAll() {
        List<User> users = List.of(UserUtils.createRandomUser(), UserUtils.createRandomUser());
        List<UserDTO> responseUsers = users.stream().map(DTOMapper::mapUserToDTO).toList();

        when(userService.findAll()).thenReturn(responseUsers);
        when(jwtService.isValidToken(anyString(), any())).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.toJsonString(responseUsers, AppUtils.SUCCESS)));
    }
}