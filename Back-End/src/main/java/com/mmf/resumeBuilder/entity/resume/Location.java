package com.mmf.resumeBuilder.entity.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "city", columnDefinition = "VARCHAR(50)")
    private String city;

    @Column(name = "country")
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location locations = (Location) o;
        return Objects.equals(city, locations.city) && Objects.equals(country, locations.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country);
    }
}
