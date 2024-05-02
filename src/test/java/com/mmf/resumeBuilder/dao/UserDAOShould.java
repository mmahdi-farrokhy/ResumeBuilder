package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.TempResumeGenerator;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = ResumeBuilderApplication.class)
public class UserDAOShould {

    User user;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setPassword("12345679");
        user.setRole(UserRole.User);

        jdbcTemplate.update("INSERT INTO app_user (email, password, role) VALUES ('user1@gmail.com', 'user1pass', 'User');");
        jdbcTemplate.update("INSERT INTO app_user (email, password, role) VALUES ('user2@gmail.com', 'user2pass', 'User');");
        jdbcTemplate.update("INSERT INTO app_user (email, password, role) VALUES ('user3@gmail.com', 'user3pass', 'User');");
        jdbcTemplate.update("INSERT INTO app_user (email, password, role) VALUES ('user4@gmail.com', 'user4pass', 'User');");
        jdbcTemplate.update("INSERT INTO app_user (email, password, role) VALUES ('user5@gmail.com', 'user5pass', 'User');");
    }

    @AfterEach
    public void tear_down() {
        jdbcTemplate.update("DELETE FROM app_user;");
    }

    @Test
    void tell_if_a_user_exists_by_its_email() {
        assertThat(userRepository.existsByEmail(user.getEmail())).isFalse();

        userRepository.save(user);
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    void find_a_user_by_its_email() {
        assertThat(userRepository.findByEmail(user.getEmail())).isNull();

        userRepository.save(user);
        User foundUser = userRepository.findByEmail(user.getEmail());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void find_a_user_with_its_resumes_by_email() {
        user.setResumes(singletonList(TempResumeGenerator.createResume()));
        userRepository.save(user);

        assertThat(userRepository.findByEmailWithResumes(user.getEmail())
                .isPresent())
                .isTrue();

        deleteEntities();
    }

    private void deleteEntities() {
        jdbcTemplate.update("DELETE FROM contact_method;");
        jdbcTemplate.update("DELETE FROM course;");
        jdbcTemplate.update("DELETE FROM education;");
        jdbcTemplate.update("DELETE FROM former_colleague;");
        jdbcTemplate.update("DELETE FROM hard_skill;");
        jdbcTemplate.update("DELETE FROM hobby;");
        jdbcTemplate.update("DELETE FROM job_experience;");
        jdbcTemplate.update("DELETE FROM language;");
        jdbcTemplate.update("DELETE FROM location;");
        jdbcTemplate.update("DELETE FROM membership;");
        jdbcTemplate.update("DELETE FROM patent;");
        jdbcTemplate.update("DELETE FROM presentation;");
        jdbcTemplate.update("DELETE FROM project;");
        jdbcTemplate.update("DELETE FROM publication;");
        jdbcTemplate.update("DELETE FROM research;");
        jdbcTemplate.update("DELETE FROM soft_skill;");
        jdbcTemplate.update("DELETE FROM teaching_assistance;");
        jdbcTemplate.update("DELETE FROM volunteer_activity;");
        jdbcTemplate.update("DELETE FROM resume;");
    }

    @Test
    void save_a_user_in_database_and_return_it() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isEqualTo(user);
        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    void update_a_user() {
        assertThat(userRepository.count()).isEqualTo(5);

        userRepository.save(user);
        assertThat(userRepository.count()).isEqualTo(6);

        user.setPassword("12345678");
        assertThat(userRepository.save(user)).isEqualTo(user);
        assertThat(userRepository.count()).isEqualTo(6);

        user.setRole(UserRole.Admin);
        assertThat(userRepository.save(user)).isEqualTo(user);
        assertThat(userRepository.count()).isEqualTo(6);

        user.setEmail("mmahdifarrokhy2@gmail.com");
        assertThat(userRepository.save(user)).isEqualTo(user);
        assertThat(userRepository.count()).isEqualTo(7);
    }

    @Test
    void read_all_users_from_database() {
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(5);

        assertThat(all.getFirst()).isEqualTo(new User(
                "user1@gmail.com",
                "user1pass",
                UserRole.User,
                null));
    }

    @Test
    void delete_a_user_by_its_email() {
        String email = "user1@gmail.com";
        assertThat(userRepository.findByEmail(email)).isNotNull();
        assertThat(userRepository.existsByEmail(email)).isTrue();

        userRepository.delete(userRepository.findByEmail(email));

        assertThat(userRepository.findByEmail(email)).isNull();
        assertThat(userRepository.existsByEmail(email)).isFalse();
    }
}
