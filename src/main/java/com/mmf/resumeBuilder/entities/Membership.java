package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id + "\n" +
                ", title='" + title + "\n" +
                ", date=" + date + "\n" +
                '}';
    }
}
