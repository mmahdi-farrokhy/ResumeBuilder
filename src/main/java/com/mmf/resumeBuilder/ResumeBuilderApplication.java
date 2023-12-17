package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.CareerDAO;
import com.mmf.resumeBuilder.dao.EducationDAO;
import com.mmf.resumeBuilder.dao.UserDAO;
import com.mmf.resumeBuilder.entities.*;
import com.mmf.resumeBuilder.enums.education.DegreeLevel;
import com.mmf.resumeBuilder.enums.education.Major;
import com.mmf.resumeBuilder.enums.job.JobCategory;
import com.mmf.resumeBuilder.enums.job.JobStatus;
import com.mmf.resumeBuilder.enums.location.City;
import com.mmf.resumeBuilder.enums.user.detail.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserDAO userDAO, CareerDAO careerDAO, EducationDAO educationDAO) {
        return runner -> addUserCareerEducation(userDAO, careerDAO, educationDAO);
    }

    private void addUserCareerEducation(UserDAO userDAO, CareerDAO careerDAO, EducationDAO educationDAO) {
        System.out.println("\nSaving user ...");
        userDAO.save(createUser(createUserDetail()));
        System.out.println("User saved!\n");

        System.out.println("\nSaving career ...");
        careerDAO.save(createCareer(createLocation()));
        System.out.println("Career saved!\n");

        System.out.println("\nSaving education ...");
        educationDAO.save(createEducation());
        System.out.println("Education saved!\n");
    }

    private Education createEducation() {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.NONE);
        education.setMajor(Major.NONE);
        education.setUniversity("");
        education.setGpa(0);
        education.setStartYear(0);
        education.setEndYear(0);
        return education;
    }

    private Location createLocation() {
        Location location = new Location();
        location.setId(1);
        location.setCityName(City.Los_Angeles);
        location.setCountryId(location.getCityName().getCountryId());
        return location;
    }

    private JobExperience createCareer(Location location) {
        JobExperience career = new JobExperience();
        career.setJobTitle("Actor");
        career.setJobCategory(JobCategory.FILM_CINEMA);
        career.setSeniorityLevel(SeniorityLevel.SENIOR);
        career.setCompanyName("Universal Pictures");
        career.setJobDescription("An actor who stared in fantastic movies like Seven, Fight Club and Inglorious Bastards");
        career.setStartDate(LocalDate.of(1992, 8, 8));
        career.setJobStatus(JobStatus.OCCUPIED);
        career.setLocation(location);
        return career;
    }

    private UserDetail createUserDetail() {
        UserDetail userDetail = new UserDetail();
        userDetail.setMaritalStatus(MaritalStatus.MARRIED);
        userDetail.setGender(Gender.MALE);
        userDetail.setMilitaryServiceStatus(MilitaryServiceStatus.NOT_SERVED_YET);
        userDetail.setBirthDate(LocalDate.of(1973, 8, 28));
        userDetail.setForeigner(false);
        userDetail.setDisabilityType(DisabilityType.NONE);
        return userDetail;
    }

    private User createUser(UserDetail userDetail) {
        User user = new User();
        user.setFirstName("Brad");
        user.setLastName("Pitt");
        user.setPhoneNumber("09123456789");
        user.setUserDetail(userDetail);
        return user;
    }
}
