package com.mmf.resumeBuilder.entities.resume;

import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "contact_method")
public class ContactMethod extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "ContactMethod{" +
                "id=" + id + "\n" +
                ", type=" + type + "\n" +
                ", value='" + value + "\n" +
                '}';
    }
}
