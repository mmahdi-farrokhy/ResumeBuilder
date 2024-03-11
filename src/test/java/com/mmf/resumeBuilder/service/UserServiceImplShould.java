package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.exception.DuplicatedEmailException;
import com.mmf.resumeBuilder.exception.ResumeNotFoundException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplShould {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    String email;
    User user;

    @Before
    public void setUp() throws Exception {
        email = "mmahdifarrokhy@gmail.com";
        user = new User(email, "12345678", UserRole.User, null);
    }

    @Test
    public void save_a_user() {
        when(userRepository.existsByEmail(email)).thenReturn(false);

        assertFalse(userService.existsByEmail(email));

        userService.saveUser(user);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(userService.existsByEmail(email));
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(capturedUser.getRole()).isEqualTo(user.getRole());
        assertThat(capturedUser.getResumes()).isNull();
    }

    @Test
    public void save_a_user_and_throw_exception_with_a_duplicated_email() {
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(userService.existsByEmail(email));

        assertThatExceptionOfType(DuplicatedEmailException.class)
                .isThrownBy(() -> userService.saveUser(user))
                .withMessage("Email already taken");

        verify(userRepository, never()).save(any());
    }

    @Test
    public void find_a_user_by_its_email() {
        when(userRepository.findByEmail(email)).thenReturn(user);
        assertThat(userService.findByEmail(email)).isEqualTo(user);
    }

    @Test
    public void find_a_user_by_its_email_and_throw_exception_if_the_email_does_not_exist() {
        when(userRepository.findByEmail(email)).thenReturn(null);

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.findByEmail(email))
                .withMessage("User with email " + email + " not found");
    }

    @Test
    public void tell_if_a_user_exists_by_its_email() {
        when(userRepository.existsByEmail(email)).thenReturn(false);
        assertFalse(userService.existsByEmail(email));
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(userService.existsByEmail(email));
    }

    @Test
    public void update_a_user() {
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertTrue(userService.existsByEmail(email));
        String newEmail = "newEmail@gmail.com";
        String newPassword = "87654321";
        UserRole newRole = UserRole.Admin;
        User newUser = new User(newEmail, newPassword, newRole, null);

        userService.updateUser(newUser, email);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByEmail(newEmail)).thenReturn(true);
        assertFalse(userService.existsByEmail(email));
        assertTrue(userService.existsByEmail(newEmail));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUSer = userArgumentCaptor.getValue();
        assertThat(capturedUSer.getEmail()).isEqualTo(newEmail);
        assertThat(capturedUSer.getPassword()).isEqualTo(newPassword);
        assertThat(capturedUSer.getRole()).isEqualTo(newRole);
        assertThat(capturedUSer.getResumes()).isNull();
    }
}
