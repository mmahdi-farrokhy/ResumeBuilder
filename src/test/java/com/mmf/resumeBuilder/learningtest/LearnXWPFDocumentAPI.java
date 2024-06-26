package com.mmf.resumeBuilder.learningtest;

import com.mmf.resumeBuilder.TempResumeGenerator;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.tools.word.documentgenerator.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LearnXWPFDocumentAPI {

    Resume resume;

    DocumentGenerator documentGenerator;

    @BeforeEach
    void setUp() {
        resume = TempResumeGenerator.createResume();
        resume.setId(1);
    }

    @Test
    void ATC_Classic_create_a_new_word_document_and_write_inside_it() {
        documentGenerator = new ATSClassicDocumentGenerator();
    }

    @Test
    void Classic_Accounting_create_a_new_word_document_and_write_inside_it() {
        documentGenerator = new ClassicAccountingDocumentGenerator();
    }

    @Test
    void Simple_Florist_create_a_new_word_document_and_write_inside_it() {
        documentGenerator = new SimpleFloristDocumentGenerator();
    }

    @Test
    void Wood_Working_create_a_new_word_document_and_write_inside_it() {
        documentGenerator = new WoodworkingDocumentGenerator();
    }

    @AfterEach
    void tearDown() {
        documentGenerator.generateWordDocument(resume);
    }
}
