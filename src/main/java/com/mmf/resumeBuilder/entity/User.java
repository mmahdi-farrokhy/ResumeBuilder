package com.mmf.resumeBuilder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.validation.NotDuplicatedEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @Column(name = "email", unique = true)
    @NotNull(message = "Email can not be empty")
    @Size(min = 1, message = "Email can not be empty")
    @Email(message = "Email address format is incorrect")
    @NotDuplicatedEmail
    private String email;

    @Column(name = "password")
    @NotNull(message = "لطفا کلمه عبور را وارد کنید")
    @Size(min = 8, message = "کلمه عبور باید حداقل 8 کاراکتر باشد")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Resume> resumes;

    public void addResume(Resume newResume) {
        if (resumes == null)
            resumes = new LinkedList<>();

        resumes.add(newResume);
        newResume.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\n' +
                ", password='" + password + '\n' +
                ", role=" + role + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }
}
