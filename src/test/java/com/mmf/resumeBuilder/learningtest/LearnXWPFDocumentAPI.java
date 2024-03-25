package com.mmf.resumeBuilder.learningtest;

import com.mmf.resumeBuilder.DatabaseTest;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.DocumentGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LearnXWPFDocumentAPI {

    Resume resume;

    @BeforeEach
    void setUp() {
        resume = DatabaseTest.createResume();
        resume.setId(1);
    }

    @Test
    void create_a_new_word_document_and_write_inside_it() {
        DocumentGenerator.createNewWordDocument(resume);
    }
}
