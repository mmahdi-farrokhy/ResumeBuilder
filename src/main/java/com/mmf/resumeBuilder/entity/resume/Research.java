package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
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
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Research research = (Research) o;
        return Objects.equals(title, research.title) && Objects.equals(publisher, research.publisher) && Objects.equals(referenceLink, research.referenceLink) && Objects.equals(date, research.date) && Objects.equals(description, research.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, referenceLink, date, description, resume);
    }
}
