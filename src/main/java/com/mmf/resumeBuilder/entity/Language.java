package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private LanguageName name;

    @Column(name = "speaking_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel speakingLanguageLevel;

    @Column(name = "writing_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel writingLanguageLevel;

    @Column(name = "reading_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel readingLanguageLevel;

    @Column(name = "listening_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel listeningLanguageLevel;

    @Column(name = "researching_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel researchingLanguageLevel;
}
