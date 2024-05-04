package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.TempResumeGenerator;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.constants.project.ProjectStatus;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.entity.resume.Project;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.repository.ResumeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = ResumeBuilderApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// NOTE: If you run all test method only the first one will pass
// and the others will throw exception, database will contain a resume
// with id greater than 1, but the child entities are inserted with a
// reference to resume with id 1
public class ResumeRepositoryShould {
    private static final String INSERT_RESUME = "INSERT INTO resume (personal_information_id, summary_id, app_user_id) VALUES (1,1,'mmahdifarrokhy@gmail.com')";
    private static final String INSERT_PERSONAL_INFORMATION = "INSERT INTO personal_information " +
            "(birth_date, disability_type, first_name, foreigner, gender, last_name, marital_status, military_service_status, phone_number) " +
            "VALUES ('1999-07-29', 'None', 'Mohammad Mahdi', false, 'Male', 'Farrokhy', 'Single', 'Exempted', '09190763415')";
    private static final String INSERT_SUMMARY = "INSERT INTO summary (text) VALUES ('I am Mohammad Mahdi a software developer with Java as my programming language');";
    private static final String INSERT_APP_USER = "INSERT INTO app_user (email, password, role) VALUES ('mmahdifarrokhy@gmail.com', '12345679', 'User');";
    private static final String INSERT_CONTACT_METHOD = "INSERT INTO contact_method (content, type, resume_id) VALUES ('mmahdifarrokhy@gmail.com', 'Email', 1)";
    private static final String INSERT_COURSE = "INSERT INTO course (credential_id, institute, name, resume_id) VALUES ('https://7learn.com/crt?h=bdP9hiSTF4', '7learn', 'Java Expert', 1)";
    private static final String INSERT_EDUCATION = "INSERT INTO education (degree_level, end_year, gpa, major, start_year, university, resume_id) VALUES ('Bachelor', 2021, 16, 'Computer_Engineering', 2017, 'Semnan University', 1)";
    private static final String INSERT_FORMER_COLLEAGUE = "INSERT INTO former_colleague (full_name, organization_name, phone_number, position, resume_id) VALUES ('Ehsan Arbabi', 'Negar Andishgan Co. Ltd.', '09123456789', 'Manager', 1)";
    private static final String INSERT_hard_skill = "INSERT INTO hard_skill (hard_skill_level, hard_skill_type, resume_id) VALUES ('Advanced', 'Java', 1)";
    private static final String INSERT_location = "INSERT INTO location (city, country) VALUES ('Pardis', 'Iran')";
    private static final String INSERT_job_experience = "INSERT INTO job_experience (category, company_name, description, end_date, seniority_level, start_date, status, title, location_id, resume_id) VALUES " +
            "('Software_Development', 'Negar Andishgan Co. Ltd.', 'I worked as a software developer on the EMG project of this company known as NrSign.EMG, debugged it, refactored some parts and added some new features to it.\n" +
            "                Finally, I added the software connection through the network and TCP protocol to this software, which received almost 2.4 megabytes of data per second from the hardware with a sampling rate of 128,000 samples per second, with the size of each sample being 19 bytes. And I process, decode and draw it in the software.', null, 'Junior', '2023-01-07', 'Occupied', 'Software Developer And Consultant', 1, 1)";
    private static final String INSERT_project = "INSERT INTO project (description, end_date, name, reference_link, start_date, status, resume_id) VALUES ('I designed a resume builder web application using Spring Boot.\n" +
            "                This app has the capabilities of creating a new resume, editing an existing resume and removing a resume from profile.\n" +
            "\n" +
            "                Stack:\n" +
            "                - Back-End: Java/Spring Boot\n" +
            "                - UI: HTML/CSS/Bootstrap\n" +
            "                - Database: MySQL', null, 'Resume Builder', 'https://github.com/mmahdi-farrokhy/ResumeBuilder', '2023-12-01', 'Active', 1)";

    User user;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Resume resume;

    @BeforeEach
    public void init() {
        user = new User();
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setPassword("12345679");
        user.setRole(UserRole.User);

        resume = TempResumeGenerator.createResume();

        jdbcTemplate.update(INSERT_PERSONAL_INFORMATION);
        jdbcTemplate.update(INSERT_SUMMARY);
        jdbcTemplate.update(INSERT_APP_USER);
        jdbcTemplate.update(INSERT_RESUME);
        jdbcTemplate.update(INSERT_CONTACT_METHOD);
        jdbcTemplate.update(INSERT_COURSE);
        jdbcTemplate.update(INSERT_EDUCATION);
        jdbcTemplate.update(INSERT_FORMER_COLLEAGUE);
        jdbcTemplate.update(INSERT_hard_skill);
        jdbcTemplate.update(INSERT_location);
        jdbcTemplate.update(INSERT_job_experience);
        jdbcTemplate.update(INSERT_project);
    }

    @AfterEach
    public void reset() {
        jdbcTemplate.update("DELETE FROM contact_method");
        jdbcTemplate.update("DELETE FROM course");
        jdbcTemplate.update("DELETE FROM project");
        jdbcTemplate.update("DELETE FROM job_experience");
        jdbcTemplate.update("DELETE FROM location");
        jdbcTemplate.update("DELETE FROM hard_skill");
        jdbcTemplate.update("DELETE FROM former_colleague");
        jdbcTemplate.update("DELETE FROM education");
        jdbcTemplate.update("DELETE FROM hobby");
        jdbcTemplate.update("DELETE FROM language");
        jdbcTemplate.update("DELETE FROM membership");
        jdbcTemplate.update("DELETE FROM patent");
        jdbcTemplate.update("DELETE FROM presentation");
        jdbcTemplate.update("DELETE FROM research");
        jdbcTemplate.update("DELETE FROM soft_skill");
        jdbcTemplate.update("DELETE FROM teaching_assistance");
        jdbcTemplate.update("DELETE FROM volunteer_activity");
        jdbcTemplate.update("DELETE FROM resume");
        jdbcTemplate.update("DELETE FROM app_user");
    }

    @Test
    @Order(1)
    void insert_resumes_to_the_database() {
        assertThat(resumeRepository.count()).isEqualTo(1);
        Resume savedResume = resumeRepository.save(resume);
        assertThat(resumeRepository.count()).isEqualTo(2);
        assertThat(savedResume).isEqualTo(resume);
    }

    @Test
    @Order(2)
    void read_all_resumes_from_database() {
        List<Resume> allResumes = new LinkedList<>();

        resumeRepository.findAll().forEach(allResumes::add);
        assertThat(allResumes.size()).isEqualTo(1);

        resumeRepository.save(resume);
        allResumes.clear();
        resumeRepository.findAll().forEach(allResumes::add);
        assertThat(allResumes.size()).isEqualTo(2);
    }

    @Test
    @Order(3)
    void find_all_resumed_by_their_user_email() {
        List<Resume> allResumesByUserEmail = resumeRepository.findAllResumesByUserEmail(user.getEmail());
        assertThat(allResumesByUserEmail.isEmpty()).isFalse();
        assertThat(allResumesByUserEmail.size()).isEqualTo(1);

        resumeRepository.save(resume);
        allResumesByUserEmail = resumeRepository.findAllResumesByUserEmail(user.getEmail());
        assertThat(allResumesByUserEmail.isEmpty()).isFalse();
        assertThat(allResumesByUserEmail.size()).isEqualTo(2);
    }

    @Test
    @Order(4)
    void update_a_resume_in_database() {
        assertThat(resumeRepository.count()).isEqualTo(1);

        resumeRepository.save(resume);
        assertThat(resumeRepository.count()).isEqualTo(2);

        Project project = new Project();
        project.setName("Coffee Shop");
        project.setDescription("An online coffee shop");
        project.setStartDate(LocalDate.of(2024, 4, 10));
        project.setStatus(ProjectStatus.Active);
        resume.addSection(project);

        resumeRepository.save(resume);
        assertThat(resumeRepository.count()).isEqualTo(2);
    }

    @Test
    @Order(5)
    void delete_a_resume_from_database() {
        assertThat(resumeRepository.findAllResumesByUserEmail(user.getEmail()).isEmpty()).isFalse();

        resumeRepository.deleteById(1);
        assertThat(resumeRepository.findAllResumesByUserEmail(user.getEmail()).size()).isEqualTo(0);
    }
}
