package com.mmf.resumeBuilder.service.wordtools.documentgenerator;

import com.mmf.resumeBuilder.entity.resume.Resume;

public interface DocumentGenerator {
    void generateWordDocument(Resume resume);
}
