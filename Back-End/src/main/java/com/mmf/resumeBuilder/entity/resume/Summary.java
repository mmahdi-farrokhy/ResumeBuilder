package com.mmf.resumeBuilder.entity.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "summary")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text", length = 2000)
    private String text;

    @Override
    public String toString() {
        return "Summary{" +
                "id=" + id + "\n" +
                ", text='" + text + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summary summary = (Summary) o;
        return Objects.equals(text, summary.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
