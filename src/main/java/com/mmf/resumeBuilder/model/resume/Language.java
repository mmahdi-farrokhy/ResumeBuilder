package com.mmf.resumeBuilder.model.resume;

import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "language")
public class Language extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private LanguageName name;

    @Column(name = "speaking_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel speakingLevel;

    @Column(name = "writing_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel writingLevel;

    @Column(name = "reading_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel readingLevel;

    @Column(name = "listening_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel listeningLevel;

    @Column(name = "researching_level")
    @Enumerated(EnumType.STRING)
    private LanguageLevel researchingLevel;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id + "\n" +
                ", name=" + name + "\n" +
                ", speakingLevel=" + speakingLevel + "\n" +
                ", writingLevel=" + writingLevel + "\n" +
                ", readingLevel=" + readingLevel + "\n" +
                ", listeningLevel=" + listeningLevel + "\n" +
                ", researchingLevel=" + researchingLevel + "\n" +
                '}';
    }
}
