package com.mmf.resumeBuilder.enums.user.detail;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum DisabilityType {
    None,
    Physical,
    Mental,
    Sensory;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
