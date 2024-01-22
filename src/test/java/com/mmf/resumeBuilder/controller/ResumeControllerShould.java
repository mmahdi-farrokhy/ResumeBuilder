package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.DatabaseTest;
import com.mmf.resumeBuilder.data.dao.ResumeDAO;
import com.mmf.resumeBuilder.data.dao.AppUserDAO;
import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.model.resume.Summary;
import com.mmf.resumeBuilder.service.ResumeService;
import com.mmf.resumeBuilder.service.UserService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeControllerShould {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    AppUserDAO userDAO;

    @MockBean
    ResumeDAO resumeDAO;

    @MockBean
    ResumeService resumeService;

    Resume resume;
    Resume expectedResume;
    private List<Resume> expectedResumes;


    @BeforeEach
    public void init() {
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('mmahdifarrokhy@gmail.com', 'Mohammadmahdi', 'Farrokhy', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('bradpitt@gmail.com', 'Brad', 'Pitt', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('davidbeckham78@gmail.com', 'David', 'Beckham', '12345679', 'User')");
        jdbcTemplate.execute("INSERT INTO app_user (email, first_name, last_name, password, role) VALUES ('arashrahmani@gmail.com', 'آرش', 'رحمانی', '12345679', 'User')");

        jdbcTemplate.execute("INSERT INTO summary (text) VALUES ('1: Some description about the person who is writing this resume')");
        jdbcTemplate.execute("INSERT INTO personal_information (first_name, last_name, phone_number, marital_status, gender, military_service_status, birth_date, foreigner, disability_type) VALUES ('Mohammad Mahdi', 'Farrokhy', '09017743009', 'Single', 'Male', 'Exempted', '1999-07-29', 0, 'None')");
        int i = countResumes();
        jdbcTemplate.execute("INSERT INTO resume (personal_information_id, summary_id) VALUES (1, 1)");

        jdbcTemplate.execute("INSERT INTO summary (text) VALUES ('2: Some description about the person who is writing this resume')");
        jdbcTemplate.execute("INSERT INTO personal_information (first_name, last_name, phone_number, marital_status, gender, military_service_status, birth_date, foreigner, disability_type) VALUES ('Mohammad Mahdi', 'Farrokhy', '09017743009', 'Single', 'Male', 'Exempted', '1999-07-29', 0, 'None')");
        jdbcTemplate.execute("INSERT INTO resume (personal_information_id, summary_id) VALUES (2,2)");

        resume = new Resume();
        resume.setId(1);
        resume.setPersonalInformation(DatabaseTest.createPersonalInformation());
        resume.addSection(DatabaseTest.createContactMethod(resume, ContactType.Email, "mmahdifarrokhy@gmail.com"));
        resume.addSection(DatabaseTest.createContactMethod(resume, ContactType.Phone_Number, "09190763415"));

        resume.setSummary(new Summary("1: Some description about the person who is writing this resume"));
        resume.addSection(DatabaseTest.createEducation(resume));
        resume.addSection(DatabaseTest.createTeachingAssistance(resume));
        resume.addSection(DatabaseTest.createTeachingAssistance2(resume));
        resume.addSection(DatabaseTest.createJobExperience1(resume));
        resume.addSection(DatabaseTest.createJobExperience2(resume));
        resume.addSection(DatabaseTest.createJobExperience3(resume));
        resume.addSection(DatabaseTest.createFormerColleague1(resume));
        resume.addSection(DatabaseTest.createFormerColleague2(resume));
        resume.addSection(DatabaseTest.createResearch(resume));
        resume.addSection(DatabaseTest.createResearch2(resume));
        resume.addSection(DatabaseTest.createCourse(resume, "Java Expert", "7learn", "https://7learn.com/crt?h=bdP9hiSTF4"));
        resume.addSection(DatabaseTest.createCourse(resume, "Spring Boot - Chad Darby", "Udemy", null));
        resume.addSection(DatabaseTest.createHardSkill(resume, HardSkillType.Java, HardSkillLevel.Advanced));
        resume.addSection(DatabaseTest.createHardSkill(resume, HardSkillType.Spring_Boot, HardSkillLevel.Intermediate));
        resume.addSection(DatabaseTest.createSoftSkill(resume, "Instruction"));
        resume.addSection(DatabaseTest.createLanguage(resume, LanguageName.Persian, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native));
        resume.addSection(DatabaseTest.createLanguage(resume, LanguageName.German, LanguageLevel.Basic, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate));
        resume.addSection(DatabaseTest.createLanguage(resume, LanguageName.English, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate));
        resume.addSection(DatabaseTest.createProject(resume));
        resume.addSection(DatabaseTest.createProject2(resume));
        resume.addSection(DatabaseTest.createPresentation(resume));
        resume.addSection(DatabaseTest.createMembership(resume, "Scientific Association of the Faculty of Computer Engineering", LocalDate.of(2019, 1, 1)));
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
        MvcResult mvcResult = mockMvc.perform(get("/resume"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertViewName(modelAndView, "resume");
        assertModelAttributeValue(modelAndView, "resumes", expectedResumes);
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
        MvcResult mvcResult = mockMvc.perform(post("/resume/edit/1")
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
