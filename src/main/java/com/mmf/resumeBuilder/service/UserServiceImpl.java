package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.data.dao.AppUserDAO;
import com.mmf.resumeBuilder.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private AppUserDAO userDAO;

    @Autowired
    public UserServiceImpl(AppUserDAO appDAO) {
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
