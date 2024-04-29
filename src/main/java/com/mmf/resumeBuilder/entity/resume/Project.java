package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmf.resumeBuilder.constants.project.ProjectStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
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

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "reference_link")
    private String referenceLink;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate) && status == project.status && Objects.equals(referenceLink, project.referenceLink) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, startDate, endDate, status, referenceLink, resume);
    }
}
