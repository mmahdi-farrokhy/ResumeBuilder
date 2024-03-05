package com.mmf.resumeBuilder.constants.hardskill;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum HardSkillLevel {
    Beginner,
    Intermediate,
    Advanced;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}