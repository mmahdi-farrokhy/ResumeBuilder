package com.mmf.resumeBuilder.entities;

import com.mmf.resumeBuilder.enums.user.detail.DisabilityType;
import com.mmf.resumeBuilder.enums.user.detail.Gender;
import com.mmf.resumeBuilder.enums.user.detail.MaritalStatus;
import com.mmf.resumeBuilder.enums.user.detail.MilitaryServiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "gender", columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "military_service_status")
    @Enumerated(EnumType.STRING)
    private MilitaryServiceStatus militaryServiceStatus;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "foreigner")
    private boolean foreigner;

    @Column(name = "disability_type", columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;
}
