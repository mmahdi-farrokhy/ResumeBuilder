package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.JobExperience;
import com.mmf.resumeBuilder.entities.Resume;
import org.springframework.data.repository.CrudRepository;

public interface ResumeDAO  extends CrudRepository<Resume, Integer> {
}
