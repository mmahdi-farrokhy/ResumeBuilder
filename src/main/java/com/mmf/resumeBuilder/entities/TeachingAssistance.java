package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "teaching_assistance")
public class TeachingAssistance extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "university")
    private String university;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "TeachingAssistance{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", university='" + university + "\n" +
                ", startDate=" + startDate + "\n" +
                ", endDate=" + endDate + "\n" +
                '}';
    }
}
