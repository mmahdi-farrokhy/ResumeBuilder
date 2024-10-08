package com.mmf.resumeBuilder.service.tools.word.documentgenerator;

import com.mmf.resumeBuilder.entity.resume.Resume;

public interface DocumentGenerator {
    void generateWordDocument(Resume resume);
}
