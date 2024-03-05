package com.mmf.resumeBuilder.constants.user.detail;

import com.mmf.resumeBuilder.constants.utils.Conversion;

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
