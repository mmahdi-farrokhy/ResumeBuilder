package com.mmf.resumeBuilder.entity;

import com.google.gson.Gson;
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

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;



    public String toJson() {
        return new Gson().toJson(this, Address.class);
    }
}
