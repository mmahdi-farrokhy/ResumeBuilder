package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.exception.DuplicatedEmailException;
import com.mmf.resumeBuilder.exception.EmailNotFoundException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedEmailException("Email already taken");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }

        return user;
    }

    @Override
    public User updateUser(User user, String userEmail) {
        User optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser != null) {
            User unwrappedUser = copyAppUser(user);
            return userRepository.save(unwrappedUser);
        } else {
            throw new UserNotFoundException(userEmail);
        }
    }

    private User copyAppUser(User sourceUser) {
        User destinationUser = new User();
        destinationUser.setEmail(sourceUser.getEmail());
        destinationUser.setPassword(sourceUser.getPassword());
        destinationUser.setRole(sourceUser.getRole());
        destinationUser.setResumes(sourceUser.getResumes());
        return destinationUser;
    }
}
