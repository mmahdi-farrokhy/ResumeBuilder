package com.mmf.resumeBuilder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mmf.resumeBuilder.TempResumeGenerator;
import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.ResumeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeControllerShould {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final String API_ENDPOINT = "/api/v1/resume";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ResumeService resumeService;

    User user;
    Resume resume;
    private List<Resume> expectedResumes;
    private List<Resume> expectedResumes2;
    private List<Resume> expectedResumes3;

    @BeforeEach
    public void init() throws CloneNotSupportedException {
        resume = TempResumeGenerator.createResume();
        Resume tmpResume = (Resume) resume.clone();
        expectedResumes = new LinkedList<>();
        expectedResumes.add(resume);
        expectedResumes.add(tmpResume);

        expectedResumes2 = new LinkedList<>();
        expectedResumes2.add(resume);

        expectedResumes3 = new LinkedList<>();
        expectedResumes3.add(tmpResume);

        user = new User();
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setPassword("123456578");
        user.setRole(UserRole.User);
        user.addResume(resume);
        user.addResume(tmpResume);

        jdbcTemplate.execute("insert into app_user (email, password, role) " +
                "values ('" + user.getEmail() + "', '" +
                user.getPassword() + "', '" +
                user.getRole() + "');");
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.execute("DELETE FROM resume");
        jdbcTemplate.execute("DELETE FROM app_user");
    }

    @Test
    @Order(1)
    void create_a_new_resume_and_save_in_database_on_post_request_to_endpoint_api_v1_resume() throws Exception {
        when(resumeService.saveResume(resume)).thenReturn(resume);

        ObjectMapper requestObjectMapper = new ObjectMapper();
        requestObjectMapper.registerModule(new JavaTimeModule());
        requestObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = requestObjectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(resume);

        MvcResult mvcResult = mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper responseObjectMapper = new ObjectMapper();
        responseObjectMapper.registerModule(new JavaTimeModule());
        responseObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Resume returnedResume = responseObjectMapper.readValue(responseJson, Resume.class);

        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeService, times(1)).saveResume(resume);

        when(resumeService.findAllResumesByUserEmail(user.getEmail())).thenReturn(singletonList(resume));
        List<Resume> allResumesByUserEmail = resumeService.findAllResumesByUserEmail(user.getEmail());
        assertThat(allResumesByUserEmail.size()).isEqualTo(1);

        verify(resumeService, times(1)).findAllResumesByUserEmail("mmahdifarrokhy@gmail.com");
    }

    @Test
    @Order(2)
    void delete_a_resume_from_database_on_delete_request_to_endpoint_api_v1_resume_id() throws Exception {
        mockMvc.perform(delete(API_ENDPOINT + "/" + resume.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        when(resumeService.findAllResumesByUserEmail(user.getEmail())).thenReturn(emptyList());
        List<Resume> allResumesByUserEmailAfterDelete = resumeService.findAllResumesByUserEmail(user.getEmail());
        assertThat(allResumesByUserEmailAfterDelete.size()).isEqualTo(0);
    }

    @Test
    @Order(3)
    void get_all_resumes_for_a_user_by_sending_a_get_request_to_endpoint_api_v1_resume_email() throws Exception {
        when(resumeService.findAllResumesByUserEmail(user.getEmail()))
                .thenReturn(Arrays.asList(resume, resume));

        MvcResult mvcResult = mockMvc.perform(get(API_ENDPOINT + "/all/" + user.getEmail()))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper responseObjectMapper = new ObjectMapper();
        responseObjectMapper.registerModule(new JavaTimeModule());
        responseObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        List<Resume> returnedResume = responseObjectMapper.readValue(
                responseJson, new TypeReference<>() {
                });

        assertThat(returnedResume).isEqualTo(Arrays.asList(resume, resume));
        verify(resumeService, times(1)).findAllResumesByUserEmail(user.getEmail());
    }

    @Test
    @Order(4)
    void update_a_resume_in_database_on_post_request_to_endpoint_api_v1_resume_id() throws Exception {
        Resume clonedResume = (Resume) resume.clone();
        clonedResume.removeSection(resume.getJobExperiences().getLast());
        when(resumeService.updateResume(clonedResume, resume.getId())).thenReturn(clonedResume);

        ObjectMapper requestObjectMapper = new ObjectMapper();
        requestObjectMapper.registerModule(new JavaTimeModule());
        requestObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = requestObjectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(clonedResume);

        MvcResult mvcResult = mockMvc.perform(put(API_ENDPOINT + "/" + resume.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper responseObjectMapper = new ObjectMapper();
        responseObjectMapper.registerModule(new JavaTimeModule());
        responseObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Resume returnedResume = responseObjectMapper.readValue(responseJson, Resume.class);

        assertThat(returnedResume).isEqualTo(clonedResume);
        verify(resumeService, times(1)).updateResume(clonedResume, resume.getId());
    }

    @Test
    @Order(4)
    void download_a_resume_from_database_on_get_request_to_endpoint_api_v1_resume_id() throws Exception {
        when(resumeService.downloadResume(resume.getId(), ResumeTheme.ATSClassic))
                .thenReturn(resume);

        MvcResult mvcResult = mockMvc.perform(get(API_ENDPOINT + "/" + resume.getId())
                        .param("theme", "ATSClassic"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        ObjectMapper responseObjectMapper = new ObjectMapper();
        responseObjectMapper.registerModule(new JavaTimeModule());
        responseObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        Resume returnedResume = responseObjectMapper.readValue(responseJson, Resume.class);

        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeService, times(1))
                .downloadResume(resume.getId(), ResumeTheme.ATSClassic);
    }
}
