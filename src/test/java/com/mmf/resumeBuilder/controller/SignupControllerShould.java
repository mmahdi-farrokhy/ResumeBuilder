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
        user.setRole(UserRole.User);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM app_user");
    }

    @Test
    @Order(1)
    void create_a_new_user_with_valid_email_using_post_request() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().hasNoErrors())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "signup-conformation-page");
    }

    @Test
    @Order(2)
    void not_validate_a_new_user_with_duplicated_email() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(3)
    void not_validate_a_new_user_with_empty_email() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("");
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(4)
    void not_validate_a_new_user_with_null_email() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail(null);
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(5)
    void not_validate_a_new_user_with_email_with_a_wrong_format() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy");
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(7)
    void not_validate_a_new_user_with_empty_password() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setPassword("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(8)
    void not_validate_a_new_user_with_null_password() throws Exception {
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setPassword(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(6)
    void not_validate_a_new_user_with_wrong_password_confirmation() throws Exception {
        user.setPasswordConfirmation("12345678");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(5)
    void not_validate_a_new_user_with_empty_first_name() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setFirstName("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(6)
    void not_validate_a_new_user_with_null_first_name() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setFirstName(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(7)
    void not_validate_a_new_user_with_empty_last_name() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setLastName("");

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }

    @Test
    @Order(8)
    void not_validate_a_new_user_with_null_last_name() throws Exception {
        user.setPasswordConfirmation("12345679");
        user.setEmail("mmahdifarrokhy2@gmail.com");
        user.setRole(UserRole.User);
        user.setLastName(null);

        MvcResult mvcResult = mockMvc.perform(post("/signup/create-user")
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
        assertViewName(modelAndView, "signup-page");
        verify(userServiceMock, times(0)).saveUser(user);
    }
}
