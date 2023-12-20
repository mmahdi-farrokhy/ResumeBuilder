package com.mmf.resumeBuilder.entities;

import com.mmf.resumeBuilder.enums.location.City;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
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
}
