package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.CareerDAO;
import com.mmf.resumeBuilder.dao.EducationDAO;
import com.mmf.resumeBuilder.dao.UserDAO;
import com.mmf.resumeBuilder.entity.*;
import com.mmf.resumeBuilder.enums.*;
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
        System.out.println("\n\nSaving user ...");
        userDAO.save(createUser(createUserDetail(createAddress())));
        System.out.println("User saved!\n\n");

        System.out.println("\n\nSaving career ...");
        careerDAO.save(createCareer(createCity(createCountry())));
        System.out.println("Career saved!\n\n");

        System.out.println("\n\nSaving education ...");
        educationDAO.save(createEducation());
        System.out.println("Education saved!\n\n");
    }

    private Education createEducation() {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.MASTER);
        education.setMajor(Major.CINEMA);
        education.setUniversity("Harvard University");
        education.setGpa(19);
        education.setStartYear(2000);
        education.setEndYear(2004);
        return education;
    }

    private City createCity(Country country) {
        City city = new City();
        city.setName("Los Angeles");
        city.setCountry(country);
        city.setId(12407);
        return city;
    }

    private Country createCountry() {
        Country country = new Country();
        country.setName("United States");
        country.setIso("US");
        return country;
    }

    private Career createCareer(City city) {
        Career career = new Career();
        career.setJobTitle("Actress");
        career.setJobCategory(JobCategory.FILM_CINEMA);
        career.setSeniorityLevel(SeniorityLevel.SENIOR);
        career.setCompanyName("Hollywood");
        career.setJobDescription("A brilliant actress staring in great movies.");
        career.setStartDate(LocalDate.of(1994, 5, 19));
        career.setEndDate(LocalDate.now());
        career.setJobStatus(JobStatus.OCCUPIED);
        career.setCity(city);
        return career;
    }

    private static User createUser(UserDetail userDetail) {
        User user = new User();
        user.setFirstName("Natalie");
        user.setLastName("Portman");
        user.setPhoneNumber("09123456789");
        user.setUserDetail(userDetail);
        return user;
    }

    private static Address createAddress() {
        Address userAddress = new Address();
        userAddress.setCity("New York");
        userAddress.setRegion("Wall St.");
        return userAddress;
    }

    private static UserDetail createUserDetail(Address userAddress) {
        UserDetail userDetail = new UserDetail();
        userDetail.setMaritalStatus(MaritalStatus.MARRIED);
        userDetail.setGender(Gender.FEMALE);
        userDetail.setMilitaryServiceStatus(MilitaryServiceStatus.COMPLETED);
        userDetail.setBirthDate(LocalDate.of(1981, 6, 9));
        userDetail.setForeigner(false);
        userDetail.setDisabilityType(DisabilityType.MENTAL);
        userDetail.setUserAddress(userAddress);
        return userDetail;
    }
}
