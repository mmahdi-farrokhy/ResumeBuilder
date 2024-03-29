package com.mmf.resumeBuilder.learningtest;

import com.mmf.resumeBuilder.DatabaseTest;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.wordtools.ATSClassicDocumentGenerator;
import com.mmf.resumeBuilder.service.wordtools.ClassicAccountingDocumentGenerator;
import com.mmf.resumeBuilder.service.wordtools.DocumentGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LearnXWPFDocumentAPI {

    Resume resume;

    DocumentGenerator documentGenerator;

    @BeforeEach
    void setUp() {
        resume = DatabaseTest.createResume();
        resume.setId(1);
    }

    @Test
    void create_a_new_word_document_and_write_inside_it_with_theme_ATC_Classic() {
        documentGenerator = new ATSClassicDocumentGenerator();
        documentGenerator.generateWordDocument(resume);
    }

    @Test
    void create_a_new_word_document_and_write_inside_it_with_theme_Classic_Accounting() {
        documentGenerator = new ClassicAccountingDocumentGenerator();
        documentGenerator.generateWordDocument(resume);
    }
}
