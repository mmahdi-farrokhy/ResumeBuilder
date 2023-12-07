package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entity.Career;
import org.springframework.data.repository.CrudRepository;

public interface CareerDAO extends CrudRepository<Career, Integer> {
}
