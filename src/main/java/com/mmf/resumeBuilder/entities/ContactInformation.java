package com.mmf.resumeBuilder.entities;

import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "contact_information")
public class ContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type")
    private ContactType type;

    @Column(name = "value")
    private String value;
}
