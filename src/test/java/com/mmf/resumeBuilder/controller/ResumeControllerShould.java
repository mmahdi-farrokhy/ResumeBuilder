package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.DatabaseTest;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.model.resume.Summary;
import com.mmf.resumeBuilder.service.ResumeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeControllerShould {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ResumeService resumeService;

    AppUser user;
    Resume resume;
    private List<Resume> expectedResumes;
    private List<Resume> expectedResumes2;
    private List<Resume> expectedResumes3;

    @BeforeEach
    public void init() throws CloneNotSupportedException {
        resume = DatabaseTest.createResume();
        Resume tmpResume = (Resume) resume.clone();
        expectedResumes = new LinkedList<>();
        expectedResumes.add(resume);
        expectedResumes.add(tmpResume);

        expectedResumes2 = new LinkedList<>();
        expectedResumes2.add(resume);

        expectedResumes3 = new LinkedList<>();
        expectedResumes3.add(tmpResume);

        user = new AppUser();
        user.setId(1);
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setPassword("123456578");
        user.setPasswordConfirmation("123456578");
        user.setRole(UserRole.User);
        user.addResume(resume);
        user.addResume(tmpResume);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM app_user");
        jdbcTemplate.execute("DELETE FROM resume");
    }

    private int countResumes() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM resume", Integer.class);
    }

    @Test
    @Order(1)
    void open_resume_html_containing_all_resumes_on_request_to_endpoint_resume() throws Exception {
        when(resumeService.findAllByUserId(1)).thenReturn(expectedResumes);
        when(resumeService.findAllByUserId(2)).thenReturn(expectedResumes2);
        when(resumeService.findAllByUserId(3)).thenReturn(expectedResumes3);

        MvcResult mvcResult = mockMvc.perform(get("/resume")
                        .flashAttr("user", user)
                        .param("id", String.valueOf(user.getId()))
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("passwordConfirmation", user.getPasswordConfirmation())
                        .param("role", user.getRole().toString()))
                .andExpect(model().attribute("user", user))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume");
        assertModelAttributeValue(modelAndView, "resumes", expectedResumes);
        verify(resumeService, times(1)).findAllByUserId(1);

        user.setId(2);
        mvcResult = mockMvc.perform(get("/resume")
                        .flashAttr("user", user)
                        .param("id", String.valueOf(user.getId()))
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("passwordConfirmation", user.getPasswordConfirmation())
                        .param("role", user.getRole().toString()))
                .andExpect(model().attribute("user", user))
                .andExpect(status().isOk())
                .andReturn();
        modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume");
        assertModelAttributeValue(modelAndView, "resumes", expectedResumes2);
        verify(resumeService, times(1)).findAllByUserId(2);

        user.setId(3);
        mvcResult = mockMvc.perform(get("/resume")
                        .flashAttr("user", user)
                        .param("id", String.valueOf(user.getId()))
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("passwordConfirmation", user.getPasswordConfirmation())
                        .param("role", user.getRole().toString()))
                .andExpect(model().attribute("user", user))
                .andExpect(status().isOk())
                .andReturn();
        modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume");
        assertModelAttributeValue(modelAndView, "resumes", expectedResumes3);
        verify(resumeService, times(1)).findAllByUserId(3);
    }

    @Test
    @Order(2)
    void redirect_to_endpoint_resume_delete_success_on_request_to_endpoint_resume_delete_id() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/resume/delete/1"))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/delete/success");
        verify(resumeService, times(1)).deleteResume(1);
    }

    @Test
    @Order(3)
    void return_resume_delete_success_html_request_to_endpoint_resume_delete_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/resume/delete/success"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume-delete-success");
    }

    @Test
    @Order(3)
    void redirect_to_endpoint_resume_download_success_on_request_to_endpoint_resume_download_id() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/resume/download/1"))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/download/success");
        verify(resumeService, times(1)).downloadResume(1);
    }

    @Test
    @Order(4)
    void return_resume_download_success_html_request_to_endpoint_resume_download_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/resume/download/success"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume-download-success");
    }

    @Test
    @Order(5)
    void redirect_to_endpoint_resume_edit_success_on_request_to_endpoint_resume_edit_id() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/resume/edit")
                        .flashAttr("resume", resume))
                .andExpect(status().is(302))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "redirect:/resume/edit/success");
        verify(resumeService, times(1)).save(resume);
    }

    @Test
    @Order(6)
    void return_resume_edit_success_html_on_request_to_endpoint_resume_edit_success() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/resume/edit/success"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume-edit-success");
    }
}
