package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.data.dao.ResumeDAO;
import com.mmf.resumeBuilder.data.dao.AppUserDAO;
import com.mmf.resumeBuilder.model.AppUser;
import com.mmf.resumeBuilder.model.resume.Resume;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    AppUser injectUser() {
        return new AppUser();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ResumeDAO resumeDAO, AppUserDAO userDAO) {
        return runner -> {
        };
    }
}
