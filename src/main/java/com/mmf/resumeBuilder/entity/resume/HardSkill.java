package com.mmf.resumeBuilder.entity.resume;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "hard_skill")
public class HardSkill extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hard_skill_type")
    @NonNull
    private HardSkillType type;

    @Column(name = "hard_skill_level")
    @Enumerated(EnumType.STRING)
    @NonNull
    private HardSkillLevel level;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    @NonNull
    @JsonIgnore
    private Resume resume;

    @Override
    public String toString() {
        return "HardSkill{" +
                "id=" + id + "\n" +
                ", type=" + type + "\n" +
                ", level=" + level + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardSkill hardSkill = (HardSkill) o;
        return type == hardSkill.type && level == hardSkill.level ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, level, resume);
    }
}
