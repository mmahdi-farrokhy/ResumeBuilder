package com.mmf.resumeBuilder.data.dao;

import com.mmf.resumeBuilder.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<AppUser, Integer> {
    boolean existsByEmail(String email);

    AppUser findByEmail(String email);
}
