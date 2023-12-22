package com.mmf.resumeBuilder.enums.user.detail;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum MilitaryServiceStatus {
    None,
    Completed,
    Exempted,
    Educational_Exemption,
    Ongoing,
    Not_Served_Yet;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
