package com.mmf.resumeBuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.repository.UserRepository;
import com.mmf.resumeBuilder.service.UserService;
import com.mmf.resumeBuilder.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResumeBuilderApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class UserControllerShould {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User user;
    User invalidUser;

    String email;
    String password;
    UserRole userRole;

    String invalidEmail;
    String invalidPassword;

    @BeforeEach
    void setUp() {
        email = "mmahdifarrokhy@gmail.com";
        password = "12345678";
        userRole = UserRole.User;
        user = new User(email, password, userRole, null);

        invalidEmail = "mmahdifarrokhy";
        invalidPassword = "123456";
        invalidUser = new User(invalidEmail, invalidPassword, userRole, null);
    }

    @Test
    void create_a_user() throws Exception {
        RequestBuilder request = post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    void create_a_user_and_response_401_bad_request_for_invalid_info() throws Exception {
        RequestBuilder request = post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void update_a_user() throws Exception {
        RequestBuilder request = put("/api/v1/user/register/" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        mockMvc.perform(request).andExpect(status().isOk());
    }
}
