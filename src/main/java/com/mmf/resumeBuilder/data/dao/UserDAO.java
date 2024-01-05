package com.mmf.resumeBuilder.data.dao;

import com.mmf.resumeBuilder.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
