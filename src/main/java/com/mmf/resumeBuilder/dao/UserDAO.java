package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Integer> {
}
