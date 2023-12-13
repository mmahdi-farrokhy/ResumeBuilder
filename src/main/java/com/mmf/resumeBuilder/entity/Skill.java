package com.mmf.resumeBuilder.entity;

import com.mmf.resumeBuilder.enums.skill.SkillCategory;
import com.mmf.resumeBuilder.enums.skill.SkillLevel;
import com.mmf.resumeBuilder.enums.skill.SkillType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "skill")
public class Skill {
    private int id;
    private SkillCategory skillCategory;
    private SkillType skillType;
    private SkillLevel skillLevel;
}
