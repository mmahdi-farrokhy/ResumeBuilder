package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.data.dao.AppUserDAO;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeDAOShould {
    @Autowired
    AppUser user;

    @MockBean
    AppUserDAO userDAO;

    @BeforeEach
    public void init() {
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setEmail("mmahdi.farrokhy@gmail.com");
        user.setRole(UserRole.User);
    }
}
