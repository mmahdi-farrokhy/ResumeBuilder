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
@Table(name = "publication")
public class Publication extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "date")
    private LocalDate date;

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
        return "Publication{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", author='" + author + "\n" +
                ", publisher='" + publisher + "\n" +
                ", date=" + date + "\n" +
                ", referenceLink='" + referenceLink + "\n" +
                ", description='" + description + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(publisher, that.publisher) && Objects.equals(date, that.date) && Objects.equals(referenceLink, that.referenceLink) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publisher, date, referenceLink, description, resume);
    }
}
