package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignupControllerShould {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    UserService userServiceMock;

    private AppUser user;

    @BeforeEach
    public void init() {
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('mmahdifarrokhy@gmail.com', 'Mohammadmahdi', 'Farrokhy', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('bradpitt@gmail.com', 'Brad', 'Pitt', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('davidbeckham78@gmail.com', 'David', 'Beckham', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('arashrahmani@gmail.com', 'آرش', 'رحمانی', '12345679', 'User')");
        user = new AppUser();
        user.setFirstName("Mohammadmahdi");
        user.setLastName("Farrokhy");
        user.setPassword("12345679");
        user.setPasswordConfirmation("12345679");
        user.setRole(UserRole.User);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM app_user");
    }

    @Test
    @Order(0)
    void open_signup_html_on_request_to_endpoint_signup() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
    }

    @Test
    @Order(1)
    void open_signup_success_html_with_valid_information_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasNoErrors())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup-success");
    }

    @Test
    @Order(2)
    void return_to_signup_html_with_duplicated_email_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy@gmail.com");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(3)
    void return_to_signup_html_with_empty_email_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(4)
    void return_to_signup_html_with_null_email_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(5)
    void return_to_signup_html_with_wrong_formatted_email_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(7)
    void return_to_signup_html_with_empty_password_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setPassword("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeErrorCount("user", 2))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(8)
    void return_to_signup_html_with_null_password_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setPassword(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeErrorCount("user", 2))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(6)
    void return_to_signup_html_with_unconfirmed_password_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setPasswordConfirmation("12345678");
        user.setEmail("mmahdifarrokhy2@gmail.com");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(5)
    void return_to_signup_html_with_empty_first_name_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setFirstName("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(6)
    void return_to_signup_html_with_null_first_name_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setFirstName(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(7)
    void return_to_signup_html_with_empty_last_name_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setLastName("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(8)
    void return_to_signup_html_with_null_last_name_on_request_to_endpoint_signup_proceed() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setLastName(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/proceed")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup");
        verify(userServiceMock, times(0)).saveUser(user);
    }
}
