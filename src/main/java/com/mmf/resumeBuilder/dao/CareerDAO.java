package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.JobExperience;
import org.springframework.data.repository.CrudRepository;

public interface CareerDAO extends CrudRepository<JobExperience, Integer> {
}
