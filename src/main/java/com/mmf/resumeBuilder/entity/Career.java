package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.job.JobCategory;
import com.mmf.resumeBuilder.enums.job.JobStatus;
import com.mmf.resumeBuilder.enums.user.detail.SeniorityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "career")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_category")
    @Enumerated(EnumType.STRING)
    private JobCategory jobCategory;

    @Column(name = "seniority_level")
    @Enumerated(EnumType.STRING)
    private SeniorityLevel seniorityLevel;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "job_status")
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @OneToOne()
    @JoinColumn(name = "location_id")
    private Location location;
}
