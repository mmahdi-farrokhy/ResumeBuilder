package com.mmf.resumeBuilder.dao;

import com.mmf.resumeBuilder.entities.ContactMethod;
import com.mmf.resumeBuilder.entities.Education;
import com.mmf.resumeBuilder.entities.Resume;
import com.mmf.resumeBuilder.entities.User;

import java.util.List;

public interface ResumeDAO {
    Resume findById(int resumeId);
    void save(Resume resume);
    User findUser(Integer resumeId);

    List<ContactMethod> findContactInformation(Integer resumeId);

    List<Education> findEducations(Integer resumeId);
}
