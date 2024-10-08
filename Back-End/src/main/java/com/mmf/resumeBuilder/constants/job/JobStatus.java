package com.mmf.resumeBuilder.constants.job;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum JobStatus {
    Finished,
    Occupied;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}