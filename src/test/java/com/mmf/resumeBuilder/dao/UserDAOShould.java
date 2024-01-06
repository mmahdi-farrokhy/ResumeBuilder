package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.ResumeBuilderApplication;
import com.mmf.resumeBuilder.model.User;
import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = ResumeBuilderApplication.class)
public class UserDAOShould {
    @Autowired
    ApplicationContext context;

    @Autowired
    User user;

    @MockBean
    UserDAO userDAO;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void init() {
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setEmail("mmahdi.farrokhy@gmail.com");
        user.setRole(UserRole.User);
    }

    @Test
    public void find_a_user_by_its_email() {
        User userByEmail = new User();
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
        User userByEmail = new User();
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
