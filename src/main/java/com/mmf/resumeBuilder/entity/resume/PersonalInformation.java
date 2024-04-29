package com.mmf.resumeBuilder.entity.resume;

import com.mmf.resumeBuilder.constants.user.detail.DisabilityType;
import com.mmf.resumeBuilder.constants.user.detail.Gender;
import com.mmf.resumeBuilder.constants.user.detail.MaritalStatus;
import com.mmf.resumeBuilder.constants.user.detail.MilitaryServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "personal_information")
public class PersonalInformation extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotBlank
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

    public String generateFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInformation that = (PersonalInformation) o;
        return foreigner == that.foreigner && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber, that.phoneNumber) && maritalStatus == that.maritalStatus && gender == that.gender && militaryServiceStatus == that.militaryServiceStatus && Objects.equals(birthDate, that.birthDate) && disabilityType == that.disabilityType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, maritalStatus, gender, militaryServiceStatus, birthDate, foreigner, disabilityType);
    }
}
