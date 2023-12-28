package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "presentation")
public class Presentation extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reference_link")
    private String referenceLink;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Presentation{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", date=" + date + "\n" +
                ", referenceLink='" + referenceLink + "\n" +
                ", description='" + description + "\n" +
                '}';
    }
}
