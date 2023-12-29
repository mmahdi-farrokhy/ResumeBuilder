package com.mmf.resumeBuilder.entities.resume;

import com.mmf.resumeBuilder.enums.location.City;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "city_name", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private City cityName;

    @Column(name = "country_id")
    private int countryId;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id + "\n" +
                ", cityName=" + cityName + "\n" +
                ", countryId=" + countryId + "\n" +
                '}';
    }
}
