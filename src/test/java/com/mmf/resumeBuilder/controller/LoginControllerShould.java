package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerShould {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    private AppUser user;

    @BeforeEach
    public void init() {
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('mmahdifarrokhy@gmail.com', 'Mohammadmahdi', 'Farrokhy', '12345679', 'User')");
        user = new AppUser();
        user.setFirstName("Mohammadmahdi");
        user.setLastName("Farrokhy");
        user.setPassword("12345679");
        user.setEmail("mmahdifarrokhy1@gmail.com");
        user.setRole(UserRole.User);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM app_user");
    }

    @Test
    @Order(1)
    void open_login_html_on_request_to_endpoint_login() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "login");
    }

    @Test
    @Order(2)
    void redirect_to_endpoint_login_success_with_valid_email_and_valid_password_on_request_to_endpoint_login_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy@gmail.com");
        MvcResult mvcResult = mockMvc.perform(get("/login/proceed")
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                )
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/login/success");
    }

    @Test
    @Order(3)
    void open_login_success_html_on_request_to_endpoint_login_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/login/success")
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                )
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "login-success");
    }

    @Test
    @Order(4)
    void redirect_to_endpoint_login_error_with_invalid_email_and_valid_password_on_request_to_endpoint_login_proceed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/login/proceed")
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                )
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/login/error");
    }

    @Test
    @Order(5)
    void redirect_to_endpoint_login_error_with_valid_email_and_invalid_password_on_request_to_endpoint_login_proceed() throws Exception {
        user.setPassword("12345678");
        MvcResult mvcResult = mockMvc.perform(get("/login/proceed")
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                )
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/login/error");
    }

    @Test
    @Order(6)
    void redirect_to_endpoint_login_error_with_invalid_email_and_invalid_password_on_request_to_endpoint_login_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy1@gmail.com");
        user.setPassword("12345678");
        MvcResult mvcResult = mockMvc.perform(get("/login/proceed")
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                )
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/login/error");
    }

    @Test
    @Order(7)
    void open_login_error_html_when_redirected_to_login_error_endpoint() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/login/error"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "login-error");
    }
}
