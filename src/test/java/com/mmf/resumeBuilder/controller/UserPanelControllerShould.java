package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
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
public class UserPanelControllerShould {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserDAO userDAO;

    private AppUser expectedUser1;
    private AppUser expectedUser2;

    @BeforeEach
    public void init() {
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('mmahdifarrokhy@gmail.com', 'Mohammadmahdi', 'Farrokhy', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('bradpitt@gmail.com', 'Brad', 'Pitt', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('davidbeckham78@gmail.com', 'David', 'Beckham', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('arashrahmani@gmail.com', 'آرش', 'رحمانی', '12345679', 'User')");

        expectedUser1 = new AppUser();
        expectedUser1.setFirstName("Mohammadmahdi");
        expectedUser1.setLastName("Farrokhy");
        expectedUser1.setPassword("12345679");
        expectedUser1.setPasswordConfirmation("12345679");
        expectedUser1.setEmail("mmahdifarrokhy@gmail.com");
        expectedUser1.setRole(UserRole.User);

        expectedUser2 = new AppUser();
        expectedUser2.setFirstName("Brad");
        expectedUser2.setLastName("Pitt");
        expectedUser2.setEmail("bradpitt@gmail.com");
        expectedUser2.setPassword("12345679");
        expectedUser2.setPasswordConfirmation("12345679");
        expectedUser2.setRole(UserRole.User);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM app_user");
    }

    @Test
    @Order(0)
    void open_user_panel_html_on_request_to_endpoint_panel_id() throws Exception {
        when(userService.fetchUser(1)).thenReturn(expectedUser1);
        when(userService.fetchUser(2)).thenReturn(expectedUser2);

        MvcResult mvcResult = mockMvc.perform(get("/panel/1"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "user-panel");
        assertModelAttributeValue(modelAndView, "user", expectedUser1);
        verify(userService, times(1)).fetchUser(1);
    }

    @Test
    void redirect_to_endpoint_resume_delete_id_on_request_to_endpoint_panel_delete_resumeid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/panel/delete/3"))
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/delete/3");
    }

    @Test
    void redirect_to_endpoint_resume_download_id_on_request_to_endpoint_panel_download_resumeid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/panel/download/3"))
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/download/3");
    }

    @Test
    void redirect_to_endpoint_resume_edit_id_on_request_to_endpoint_panel_edit_resumeid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/panel/edit/3"))
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/edit/3");
    }

    @Test
    void redirect_to_endpoint_resume_share_id_on_request_to_endpoint_panel_share_resumeid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/panel/share/3"))
                .andExpect(status().is(302))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/share/3");
    }

    @Test
    @Order(0)
    void open_user_profile_html_on_request_to_endpoint_panel_id_profile() throws Exception {
        when(userService.fetchUser(1)).thenReturn(expectedUser1);
        when(userService.fetchUser(2)).thenReturn(expectedUser2);

        MvcResult mvcResult = mockMvc.perform(get("/panel/2/profile"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "user-profile");
        assertModelAttributeValue(modelAndView, "user", expectedUser2);
        verify(userService, times(1)).fetchUser(2);
    }

    @Test
    @Order(1)
    void redirect_to_endpoint_panel_profile_update_success_on_request_to_endpoint_panel_profile_update() throws Exception {
        AppUser updatedUser = new AppUser();
        updatedUser.setFirstName("Mohammad Mahdi");
        updatedUser.setLastName(expectedUser1.getLastName());
        updatedUser.setEmail(expectedUser1.getEmail());
        updatedUser.setPassword(expectedUser1.getPassword());
        updatedUser.setPasswordConfirmation(expectedUser1.getPasswordConfirmation());
        updatedUser.setRole(expectedUser1.getRole());

        when(userService.fetchUser(1)).thenReturn(expectedUser1);
        AppUser appUser = userService.fetchUser(1);
        assertEquals(appUser, expectedUser1);

        MvcResult mvcResult = mockMvc.perform(post("/panel/profile/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("user", updatedUser))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", updatedUser))
                .andExpect(model().hasNoErrors())
                .andReturn();

        when(userService.fetchUser(1)).thenReturn(updatedUser);
        appUser = userService.fetchUser(1);
        assertEquals(appUser, updatedUser);

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "profile-update-success");
        verify(userService, times(1)).saveUser(updatedUser);
    }
}
