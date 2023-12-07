package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Integer> {
}
