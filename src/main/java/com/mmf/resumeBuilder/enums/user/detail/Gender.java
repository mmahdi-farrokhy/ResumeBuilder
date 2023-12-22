package com.mmf.resumeBuilder.enums.user.detail;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum Gender {
    Male,
    Female,
    Others;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
