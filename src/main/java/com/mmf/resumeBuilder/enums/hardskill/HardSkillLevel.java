package com.mmf.resumeBuilder.enums.hardskill;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum HardSkillLevel {
    Beginner,
    Intermediate,
    Advanced;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
