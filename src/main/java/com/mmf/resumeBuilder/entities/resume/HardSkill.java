package com.mmf.resumeBuilder.entities.resume;

import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "hard_skill")
public class HardSkill extends ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hard_skill_type")
    private HardSkillType type;

    @Column(name = "hard_skill_level")
    @Enumerated(EnumType.STRING)
    private HardSkillLevel level;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Override
    public String toString() {
        return "HardSkill{" +
                "id=" + id + "\n" +
                ", type=" + type + "\n" +
                ", level=" + level + "\n" +
                '}';
    }
}
