package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "former_colleague")
public class FormerColleague extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    @JsonIgnore
    private Resume resume;

    @Override
    public String toString() {
        return "FormerColleague{" +
                "id=" + id + "\n" +
                ", fullName='" + fullName + "\n" +
                ", position='" + position + "\n" +
                ", organizationName='" + organizationName + "\n" +
                ", phoneNumber='" + phoneNumber + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormerColleague that = (FormerColleague) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(position, that.position) && Objects.equals(organizationName, that.organizationName) && Objects.equals(phoneNumber, that.phoneNumber) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, position, organizationName, phoneNumber, resume);
    }
}
