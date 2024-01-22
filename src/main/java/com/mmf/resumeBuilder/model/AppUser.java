package com.mmf.resumeBuilder.model;

import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.model.resume.Resume;
import com.mmf.resumeBuilder.validation.AvailableEmail;
import com.mmf.resumeBuilder.validation.PasswordMatch;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@PasswordMatch
@Entity
@Table(name = "app_user")
public class AppUser {
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

    @Column(name = "email", unique = true)
    @NotNull(message = "لطفا ایمیل خود را وارد کنید")
    @Size(min = 1, message = "لطفا ایمیل خود را وارد کنید")
    @Email(message = "فرمت ایمیل اشتباه است")
    @AvailableEmail
    private String email;

    @Column(name = "password")
    @NotNull(message = "لطفا کلمه عبور را وارد کنید")
    @Size(min = 8, message = "کلمه عبور باید حداقل 8 کاراکتر باشد")
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Resume> resumes;

    public void addResume(Resume newResume) {
        if (resumes == null)
            resumes = new LinkedList<>();

        resumes.add(newResume);
        newResume.setAppUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\n' +
                ", lastName='" + lastName + '\n' +
                ", email='" + email + '\n' +
                ", password='" + password + '\n' +
                ", passwordConfirmation='" + passwordConfirmation + '\n' +
                ", role=" + role + '\n' +
                '}';
    }
}
