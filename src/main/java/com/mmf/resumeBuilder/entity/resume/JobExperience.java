package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmf.resumeBuilder.constants.job.JobCategory;
import com.mmf.resumeBuilder.constants.job.JobStatus;
import com.mmf.resumeBuilder.constants.user.detail.SeniorityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "job_experience")
public class JobExperience extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @Column(name = "seniority_level")
    @Enumerated(EnumType.STRING)
    private SeniorityLevel seniorityLevel;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    @JsonIgnore
    private Resume resume;

    @Override
    public String toString() {
        return "JobExperience{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", category=" + category + "\n" +
                ", seniorityLevel=" + seniorityLevel + "\n" +
                ", companyName='" + companyName + "\n" +
                ", description='" + description + "\n" +
                ", startDate=" + startDate + "\n" +
                ", endDate=" + endDate + "\n" +
                ", status=" + status + "\n" +
                ", location=" + location + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExperience that = (JobExperience) o;
        return Objects.equals(title, that.title) && category == that.category && seniorityLevel == that.seniorityLevel && Objects.equals(companyName, that.companyName) && Objects.equals(description, that.description) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && status == that.status && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, seniorityLevel, companyName, description, startDate, endDate, status, location, resume);
    }
}
