package com.mmf.resumeBuilder.entities.resume;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "patent")
public class Patent extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "reference_link")
    private String referenceLink;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Patent{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", registrationNumber='" + registrationNumber + "\n" +
                ", registrationDate=" + registrationDate + "\n" +
                ", referenceLink='" + referenceLink + "\n" +
                ", description='" + description + "\n" +
                '}';
    }
}
