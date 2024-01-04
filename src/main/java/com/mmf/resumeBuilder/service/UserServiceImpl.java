package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO appDAO;

    @Autowired
    public UserServiceImpl(UserDAO appDAO) {
        this.appDAO = appDAO;
    }

    @Override
    public void saveUser(User user) {
        appDAO.save(user);
    }

    @Override
    public User fetchUser(Integer userId) {
        return appDAO.findById(userId).get();
    }
}
