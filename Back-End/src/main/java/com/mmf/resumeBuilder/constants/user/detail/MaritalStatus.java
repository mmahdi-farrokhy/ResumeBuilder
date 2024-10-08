package com.mmf.resumeBuilder.constants.user.detail;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum MaritalStatus {
    Single,
    Married;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
