package com.mmf.resumeBuilder.entity.resume;

import com.mmf.resumeBuilder.constants.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
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
