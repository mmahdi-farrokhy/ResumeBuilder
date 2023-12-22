package com.mmf.resumeBuilder.enums.education;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum DegreeLevel {
    None,
    Associate,
    Bachelor,
    Master,
    Phd;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
