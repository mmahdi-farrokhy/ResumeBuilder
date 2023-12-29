package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.ResumeDAO;
import com.mmf.resumeBuilder.entities.*;
import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import com.mmf.resumeBuilder.enums.education.DegreeLevel;
import com.mmf.resumeBuilder.enums.education.Major;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import com.mmf.resumeBuilder.enums.job.JobCategory;
import com.mmf.resumeBuilder.enums.job.JobStatus;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.enums.location.City;
import com.mmf.resumeBuilder.enums.project.ProjectStatus;
import com.mmf.resumeBuilder.enums.user.detail.*;
import org.hibernate.boot.model.relational.Database;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ResumeDAO resumeDAO) {
        return runner -> {
            DatabaseTest.findResumeAndItsSectionsInDatabase(resumeDAO);
        };
    }
}
