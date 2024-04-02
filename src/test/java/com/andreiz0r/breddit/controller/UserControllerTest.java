package com.andreiz0r.breddit.controller;

import com.andreiz0r.breddit.service.UserService;
import com.andreiz0r.breddit.utils.WithMockUser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//TODO:

//@SpringBootTest
//@AutoConfigureMockMvc
@SpringBootTest(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    @WithMockUser(roles = "Mod")
    void findAll() {
//        List<User> users = List.of(UserUtils.createRandomUser(), UserUtils.createRandomUser());
//        List<UserDTO> responseUsers = users.stream().map(DTOMapper::mapUserToDTO).toList();
//
//        when(userService.findAll()).thenReturn(responseUsers);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andDo(print())
//                .andExpect(status().isOk());
    }
}