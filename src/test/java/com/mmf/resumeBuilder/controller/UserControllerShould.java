package com.mmf.resumeBuilder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
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

    String password = "12345678";
    UserRole userRole = UserRole.User;
    String email = "mmahdifarrokhy@gmail.com";

    final String API_ENDPOINT = "/api/v1/user";

    @BeforeEach
    void setUp() {
        user = new User(email, password, userRole, null);
    }

    @Test
    void create_a_new_user_and_save_in_database_on_post_request_to_endpoint_api_v1_user() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);

        RequestBuilder request = post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        User returnedUser = new ObjectMapper().readValue(responseJson, User.class);
        assertThat(returnedUser).isEqualTo(user);
    }

    @Test
    void create_a_user_and_response_401_bad_request_for_invalid_info() throws Exception {
        User invalidUser = new User("mmahdifarrokhy", "12345678", UserRole.User, null);
        when(userService.saveUser(invalidUser)).thenReturn(null);

        RequestBuilder request = post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        assertThat(responseJson).isEmpty();

        invalidUser = new User("mmahdifarrokhy@gmail.com", "1234567", UserRole.User, null);
        when(userService.saveUser(invalidUser)).thenReturn(null);

        request = post(API_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser));

        mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        responseJson = mvcResult.getResponse().getContentAsString();
        assertThat(responseJson).isEmpty();
    }

    @Test
    void update_a_user() throws Exception {
        RequestBuilder request = put(API_ENDPOINT + "/" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void update_a_user_and_response_401_bad_request_for_invalid_info() throws Exception {
        User invalidUser = new User("mmahdifarrokhy", "12345678", UserRole.User, null);
        when(userService.saveUser(invalidUser)).thenReturn(null);

        RequestBuilder request = put(API_ENDPOINT + "/" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser));

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        assertThat(responseJson).isEmpty();

        invalidUser = new User("mmahdifarrokhy@gmail.com", "1234567", UserRole.User, null);
        when(userService.saveUser(invalidUser)).thenReturn(null);

        request = put(API_ENDPOINT + "/" + email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser));

        mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        responseJson = mvcResult.getResponse().getContentAsString();
        assertThat(responseJson).isEmpty();
    }
}
