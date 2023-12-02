package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.DisabilityType;
import com.mmf.resumeBuilder.enums.Gender;
import com.mmf.resumeBuilder.enums.MaritalStatus;
import com.mmf.resumeBuilder.enums.MilitaryServiceStatus;
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

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "military_service_status")
    @Enumerated(EnumType.STRING)
    private MilitaryServiceStatus militaryServiceStatus;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "foreigner")
    private boolean foreigner;

    @Column(name = "disability_type")
    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    private Address userAddress;
}
