package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.ResumeDAO;
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
    public CommandLineRunner commandLineRunner(ResumeDAO resumeDAO) {
        return runner -> {
        };
    }
}
