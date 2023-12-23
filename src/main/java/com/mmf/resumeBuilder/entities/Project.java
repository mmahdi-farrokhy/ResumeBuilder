package com.mmf.resumeBuilder.entities;

import com.mmf.resumeBuilder.enums.project.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "reference_link")
    private String referenceLink;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id + "\n" +
                ", name='" + name + "\n" +
                ", description='" + description + "\n" +
                ", startDate=" + startDate + "\n" +
                ", endDate=" + endDate + "\n" +
                ", status=" + status + "\n" +
                ", referenceLink='" + referenceLink + "\n" +
                '}';
    }
}
