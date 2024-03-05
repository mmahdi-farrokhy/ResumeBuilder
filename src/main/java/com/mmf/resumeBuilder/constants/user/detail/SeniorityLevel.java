package com.mmf.resumeBuilder.constants.user.detail;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum SeniorityLevel {
    Worker,
    Employee,
    Specialist,
    Senior,
    Manager,
    Director,
    Business,
    Head,
    Junior,
    Mid_Level,
    Intern,
    Ceo;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
