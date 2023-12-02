package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.entity.Address;
import com.mmf.resumeBuilder.dao.AppDAO;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.entity.UserDetail;
import com.mmf.resumeBuilder.enums.DisabilityType;
import com.mmf.resumeBuilder.enums.Gender;
import com.mmf.resumeBuilder.enums.MaritalStatus;
import com.mmf.resumeBuilder.enums.MilitaryServiceStatus;
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
    public CommandLineRunner commandLineRunner(AppDAO appDAO) {
        return runner -> addUser(appDAO);
    }

    private void addUser(AppDAO appDAO) {
        Address userAddress = new Address();
        userAddress.setCity("Tehran");
        userAddress.setRegion("Shush st.");

        UserDetail userDetail = new UserDetail();
        userDetail.setMaritalStatus(MaritalStatus.MARRIED);
        userDetail.setGender(Gender.MALE);
        userDetail.setMilitaryServiceStatus(MilitaryServiceStatus.COMPLETED);
        userDetail.setBirthDate(LocalDate.of(1960, 12, 11));
        userDetail.setForeigner(false);
        userDetail.setDisabilityType(DisabilityType.NONE);
        userDetail.setUserAddress(userAddress);

        User user = new User();
        user.setFirstName("Asghar");
        user.setLastName("Ghasab");
        user.setPhoneNumber("09123456789");
        user.setUserDetail(userDetail);

        appDAO.save(user);
    }
}
