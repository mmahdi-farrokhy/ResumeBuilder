package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.location.City;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "city", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(name = "region")
    private String region;
}
