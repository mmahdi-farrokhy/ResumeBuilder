package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_detail_id")
    private UserDetail userDetail;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + "\n" +
                ", firstName='" + firstName +"\n" +
                ", lastName='" + lastName  +"\n" +
                ", phoneNumber='" + phoneNumber +"\n" +
                ", userDetail=" + userDetail +
                '}';
    }
}
