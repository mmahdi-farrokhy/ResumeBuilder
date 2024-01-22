package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.data.dao.AppUserDAO;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.AppUser;
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

    @Test
    public void find_a_user_by_its_email() {
        AppUser userByEmail = new AppUser();
        userByEmail.setFirstName("Brad");
        userByEmail.setLastName("Pitt");
        userByEmail.setPassword("qwe123");
        userByEmail.setEmail("bradpitt@gmail.com");
        userByEmail.setRole(UserRole.User);
        when(userDAO.findByEmail(userByEmail.getEmail())).thenReturn(userByEmail);

        assertEquals(userByEmail.getFirstName(), userDAO.findByEmail("bradpitt@gmail.com").getFirstName());
        assertEquals(userByEmail.getLastName(), userDAO.findByEmail("bradpitt@gmail.com").getLastName());
        assertEquals(userByEmail.getPassword(), userDAO.findByEmail("bradpitt@gmail.com").getPassword());
        assertEquals(userByEmail.getRole(), userDAO.findByEmail("bradpitt@gmail.com").getRole());
        assertEquals(userByEmail.getEmail(), userDAO.findByEmail("bradpitt@gmail.com").getEmail());

        verify(userDAO, times(5)).findByEmail("bradpitt@gmail.com");
    }

    @Test
    public void return_null_for_an_invalid_email() {
        AppUser userByEmail = new AppUser();
        userByEmail.setFirstName("Brad");
        userByEmail.setLastName("Pitt");
        userByEmail.setPassword("qwe123");
        userByEmail.setEmail("bradpitt5@gmail.com");
        userByEmail.setRole(UserRole.User);
        when(userDAO.findByEmail(userByEmail.getEmail())).thenReturn(null);

        assertNull(userDAO.findByEmail("bradpitt@gmail.com"));
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getFirstName());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getLastName());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getEmail());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getPassword());
        assertThrows(NullPointerException.class, () -> userDAO.findByEmail("bradpitt@gmail.com").getRole());

        verify(userDAO, times(6)).findByEmail("bradpitt@gmail.com");
    }
}
