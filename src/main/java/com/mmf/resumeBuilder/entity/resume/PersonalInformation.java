package com.mmf.resumeBuilder.entity.resume;

import com.mmf.resumeBuilder.constants.user.detail.DisabilityType;
import com.mmf.resumeBuilder.constants.user.detail.Gender;
import com.mmf.resumeBuilder.constants.user.detail.MaritalStatus;
import com.mmf.resumeBuilder.constants.user.detail.MilitaryServiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "personal_information")
public class PersonalInformation extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "gender", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "military_service_status")
    @Enumerated(EnumType.STRING)
    private MilitaryServiceStatus militaryServiceStatus;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "foreigner")
    private boolean foreigner;

    @Column(name = "disability_type", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\n' +
                ", lastName='" + lastName + '\n' +
                ", phoneNumber='" + phoneNumber + '\n' +
                ", maritalStatus=" + maritalStatus + "\n" +
                ", gender=" + gender + "\n" +
                ", militaryServiceStatus=" + militaryServiceStatus + "\n" +
                ", birthDate=" + birthDate + "\n" +
                ", foreigner=" + foreigner + "\n" +
                ", disabilityType=" + disabilityType + "\n" +
                '}';
    }
}
