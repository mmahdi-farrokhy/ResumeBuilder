package com.mmf.resumeBuilder.enums.job;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum JobStatus {
    FINISHED,
    OCCUPIED;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
