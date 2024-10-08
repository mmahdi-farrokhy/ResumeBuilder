package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmf.resumeBuilder.constants.language.LanguageLevel;
import com.mmf.resumeBuilder.constants.language.LanguageName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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
    @JsonIgnore
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

    public LanguageLevel estimateAverageLevel() {
        int upperIntermediateLevels = countUpperIntermediateLevels();
        LanguageLevel averageLevel;

        if (this.speakingLevel == LanguageLevel.Native ||
                this.writingLevel == LanguageLevel.Native ||
                this.readingLevel == LanguageLevel.Native ||
                this.listeningLevel == LanguageLevel.Native ||
                this.researchingLevel == LanguageLevel.Native) {
            averageLevel = LanguageLevel.Native;
        } else if (upperIntermediateLevels == 5) {
            averageLevel = LanguageLevel.Advanced;
        } else if (upperIntermediateLevels == 4) {
            averageLevel = LanguageLevel.Upper_Intermediate;
        } else if (upperIntermediateLevels == 3) {
            averageLevel = LanguageLevel.Intermediate;
        } else if (upperIntermediateLevels == 2) {
            averageLevel = LanguageLevel.Pre_Intermediate;
        } else {
            averageLevel = LanguageLevel.Basic;
        }

        return averageLevel;
    }

    private int countUpperIntermediateLevels() {
        int upperIntermediateLevels = 0;

        if (this.speakingLevel.compareTo(LanguageLevel.Intermediate) > 0)
            upperIntermediateLevels++;

        if (this.writingLevel.compareTo(LanguageLevel.Intermediate) > 0)
            upperIntermediateLevels++;

        if (this.readingLevel.compareTo(LanguageLevel.Intermediate) > 0)
            upperIntermediateLevels++;

        if (this.listeningLevel.compareTo(LanguageLevel.Intermediate) > 0)
            upperIntermediateLevels++;

        if (this.readingLevel.compareTo(LanguageLevel.Intermediate) > 0)
            upperIntermediateLevels++;
        return upperIntermediateLevels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return name == language.name && speakingLevel == language.speakingLevel && writingLevel == language.writingLevel && readingLevel == language.readingLevel && listeningLevel == language.listeningLevel && researchingLevel == language.researchingLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, speakingLevel, writingLevel, readingLevel, listeningLevel, researchingLevel, resume);
    }
}
