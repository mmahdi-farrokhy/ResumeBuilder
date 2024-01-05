package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO appDAO) {
        this.userDAO = appDAO;
    }

    @Override
    public void saveUser(User user) {
        userDAO.save(user);
    }

    @Override
    public User fetchUser(Integer userId) {
        return userDAO.findById(userId).get();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
