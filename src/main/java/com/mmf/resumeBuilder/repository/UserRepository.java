package com.mmf.resumeBuilder.repository;

import com.mmf.resumeBuilder.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query("SELECT DISTINCT au FROM User au LEFT JOIN FETCH au.resumes WHERE au.email = :userEmail")
    Optional<User> findByEmailWithResumes(@Param("userEmail") Integer userEmail);
}
