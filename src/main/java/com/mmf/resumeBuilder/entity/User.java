package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.validation.NotDuplicatedEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
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
                "email='" + email + '\n' +
                ", password='" + password + '\n' +
                ", role=" + role + '\n' +
                '}';
    }
}
