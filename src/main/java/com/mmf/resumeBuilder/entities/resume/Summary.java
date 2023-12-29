package com.mmf.resumeBuilder.entities.resume;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "summary")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Override
    public String toString() {
        return "Summary{" +
                "id=" + id + "\n" +
                ", text='" + text + "\n" +
                '}';
    }
}
