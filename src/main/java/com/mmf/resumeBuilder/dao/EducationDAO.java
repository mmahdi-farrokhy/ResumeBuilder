package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.Education;
import org.springframework.data.repository.CrudRepository;

public interface EducationDAO extends CrudRepository<Education, Integer> {
}
