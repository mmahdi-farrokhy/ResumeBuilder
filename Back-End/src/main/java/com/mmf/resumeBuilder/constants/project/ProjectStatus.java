package com.mmf.resumeBuilder.constants.project;

import com.mmf.resumeBuilder.constants.utils.Conversion;

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
