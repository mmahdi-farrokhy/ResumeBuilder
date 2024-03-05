package com.mmf.resumeBuilder.entity.resume;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "research")
public class Research extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "reference_link")
    private String referenceLink;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Research{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", publisher='" + publisher + "\n" +
                ", referenceLink='" + referenceLink + "\n" +
                ", date=" + date + "\n" +
                ", description='" + description + "\n" +
                '}';
    }
}
