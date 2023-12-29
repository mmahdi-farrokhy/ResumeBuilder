package com.mmf.resumeBuilder.entities.resume;

import com.mmf.resumeBuilder.enums.education.DegreeLevel;
import com.mmf.resumeBuilder.enums.education.Major;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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
    private Resume resume;

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id + "\n" +
                ", degreeLevel=" + degreeLevel +"\n" +
                ", major=" + major +"\n" +
                ", university='" + university + "\n" +
                ", gpa=" + gpa +"\n" +
                ", startYear=" + startYear +"\n" +
                ", endYear=" + endYear +"\n" +
                '}';
    }
}
