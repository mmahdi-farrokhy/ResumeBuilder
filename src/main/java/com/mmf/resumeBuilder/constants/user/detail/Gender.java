package com.mmf.resumeBuilder.constants.user.detail;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum Gender {
    Male,
    Female,
    Others;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
