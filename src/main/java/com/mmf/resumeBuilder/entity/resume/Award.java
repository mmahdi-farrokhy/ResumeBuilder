package com.mmf.resumeBuilder.entity.resume;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "award")
public class Award extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Award{" + "id=" + id + "\n" + ", title='" + title + "\n" + ", year=" + year + "\n" + '}';
    }
}
