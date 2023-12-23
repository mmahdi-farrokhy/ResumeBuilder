package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "soft_skill")
public class SoftSkill extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "SoftSkill{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                '}';
    }
}
