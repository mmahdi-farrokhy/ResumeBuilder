package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.repository.UserRepository;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = ResumeBuilderApplication.class)
public class UserDAOShould {

    @Autowired
    User user;

    @MockBean
    UserRepository userDAO;

    @BeforeEach
    public void init() {
        user.setEmail("mmahdi.farrokhy@gmail.com");
        user.setRole(UserRole.User);
    }

    @Test
    public void find_a_user_by_its_email() {
        User userByEmail = new User();
        userByEmail.setPassword("qwe123");
        userByEmail.setEmail("bradpitt@gmail.com");
        userByEmail.setRole(UserRole.User);
        when(userDAO.findByEmail(userByEmail.getEmail())).thenReturn(userByEmail);

        assertEquals(userByEmail.getPassword(), userDAO.findByEmail("bradpitt@gmail.com").getPassword());
        assertEquals(userByEmail.getRole(), userDAO.findByEmail("bradpitt@gmail.com").getRole());
        assertEquals(userByEmail.getEmail(), userDAO.findByEmail("bradpitt@gmail.com").getEmail());

        verify(userDAO, times(5)).findByEmail("bradpitt@gmail.com");
    }

    @Test
    public void return_null_for_an_invalid_email() {
        User userByEmail = new User();
        userByEmail.setPassword("qwe123");
        userByEmail.setEmail("bradpitt5@gmail.com");
        userByEmail.setRole(UserRole.User);
        when(userDAO.findByEmail(userByEmail.getEmail())).thenReturn(null);

        assertNull(userDAO.findByEmail("bradpitt@gmail.com"));
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getEmail());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getPassword());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getRole());

        verify(userDAO, times(6)).findByEmail("bradpitt@gmail.com");
    }
}
