package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.DegreeLevel;
import com.mmf.resumeBuilder.enums.Major;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "degree_level")
    private DegreeLevel degreeLevel;

    @Column(name = "major")
    private Major major;

    @Column(name = "university")
    private String university;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "end_year")
    private int endYear;
}
