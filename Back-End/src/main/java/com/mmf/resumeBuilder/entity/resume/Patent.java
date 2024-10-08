package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
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
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patent patent = (Patent) o;
        return Objects.equals(title, patent.title) && Objects.equals(registrationNumber, patent.registrationNumber) && Objects.equals(registrationDate, patent.registrationDate) && Objects.equals(referenceLink, patent.referenceLink) && Objects.equals(description, patent.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, registrationNumber, registrationDate, referenceLink, description, resume);
    }
}
