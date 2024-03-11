package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.repository.UserRepository;
import com.mmf.resumeBuilder.repository.ResumeRepository;
import com.mmf.resumeBuilder.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    User injectUser() {
        return new User();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ResumeRepository resumeDAO, UserRepository userDAO) {
        return runner -> {
        };
    }
}
