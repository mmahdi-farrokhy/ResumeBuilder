package com.mmf.resumeBuilder.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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
}
