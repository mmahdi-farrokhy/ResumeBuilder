package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.exception.DuplicatedEmailException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicatedEmailException("Email already taken");
        }

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
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException(userEmail);
        }
    }
}
