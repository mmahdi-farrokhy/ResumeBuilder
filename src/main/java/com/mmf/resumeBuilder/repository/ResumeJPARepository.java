package com.mmf.resumeBuilder.repository;

import com.mmf.resumeBuilder.entity.resume.Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeJPARepository extends CrudRepository<Resume, Integer> {
    @Query("SELECT u.resumes FROM User u WHERE u.email = :userEmail")
    Optional<List<Resume>> findAllResumesByUserEmail(@Param("userEmail") String userEmail);
}
