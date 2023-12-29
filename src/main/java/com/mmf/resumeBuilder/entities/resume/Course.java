package com.mmf.resumeBuilder.entities.resume;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "institute")
    private String institute;

    @Column(name = "credential_id")
    private String credentialId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id + "\n" +
                ", name='" + name + "\n" +
                ", institute='" + institute + "\n" +
                ", credentialId='" + credentialId + "\n" +
                '}';
    }
}
