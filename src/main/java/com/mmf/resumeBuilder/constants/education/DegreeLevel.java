package com.mmf.resumeBuilder.constants.education;

import com.mmf.resumeBuilder.constants.utils.Conversion;

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
