package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.model.AppUser;
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
    public void saveUser(AppUser user) {
        userDAO.save(user);
    }

    @Override
    public AppUser fetchUser(Integer userId) {
        return userDAO.findById(userId).get();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public AppUser findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
