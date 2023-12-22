package com.mmf.resumeBuilder.enums.project;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum ProjectStatus {
    Active,
    Completed,
    Cancelled,
    On_Hold;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
