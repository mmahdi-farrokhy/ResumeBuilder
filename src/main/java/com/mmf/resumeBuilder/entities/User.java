package com.mmf.resumeBuilder.entities;

import com.mmf.resumeBuilder.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @NotNull(message = "لطفا نام خود را وارد کنید")
    @Size(min = 1, message = "لطفا نام خود را وارد کنید")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "لطفا نام خانوادگی خود را وارد کنید")
    @Size(min = 1, message = "لطفا نام خانوادگی خود را وارد کنید")
    private String lastName;

    @Column(name = "email")
    @NotNull(message = "لطفا ایمیل خود را وارد کنید")
    @Size(min = 1, message = "لطفا ایمیل خود را وارد کنید")
    private String email;

    @Column(name = "password")
    @NotNull(message = "لطفا کلمه عبور را وارد کنید")
    @Size(min = 1, message = "لطفا کلمه عبور را وارد کنید")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\n' +
                ", lastName='" + lastName + '\n' +
                ", email='" + email + '\n' +
                ", password='" + password + '\n' +
                ", role=" + role + '\n' +
                '}';
    }
}
