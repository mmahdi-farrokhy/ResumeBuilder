package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmf.resumeBuilder.constants.education.DegreeLevel;
import com.mmf.resumeBuilder.constants.education.Major;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "degree_level")
    @Enumerated(EnumType.STRING)
    private DegreeLevel degreeLevel;

    @Column(name = "major")
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(name = "university")
    private String university;

    @Column(name = "gpa")
    private double gpa;

    @Column(name = "start_year")
    private int startYear;

    @Column(name = "end_year")
    private int endYear;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    @JsonIgnore
    private Resume resume;

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id + "\n" +
                ", degreeLevel=" + degreeLevel + "\n" +
                ", major=" + major + "\n" +
                ", university='" + university + "\n" +
                ", gpa=" + gpa + "\n" +
                ", startYear=" + startYear + "\n" +
                ", endYear=" + endYear + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Double.compare(gpa, education.gpa) == 0 && startYear == education.startYear && endYear == education.endYear && degreeLevel == education.degreeLevel && major == education.major && Objects.equals(university, education.university) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degreeLevel, major, university, gpa, startYear, endYear, resume);
    }
}
