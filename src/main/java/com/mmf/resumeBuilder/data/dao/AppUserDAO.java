package com.mmf.resumeBuilder.data.dao;

import com.mmf.resumeBuilder.model.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserDAO extends CrudRepository<AppUser, Integer> {
    boolean existsByEmail(String email);

    AppUser findByEmail(String email);

    @Query("SELECT DISTINCT au FROM AppUser au LEFT JOIN FETCH au.resumes WHERE au.id = :userId")
    Optional<AppUser> findByIdWithResumes(@Param("userId") Integer userId);
}
