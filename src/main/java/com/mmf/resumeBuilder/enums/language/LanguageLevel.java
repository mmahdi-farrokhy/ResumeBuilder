package com.mmf.resumeBuilder.enums.language;

import com.mmf.resumeBuilder.enums.utils.Conversion;
import lombok.Getter;

@Getter
public enum LanguageLevel {
    Basic("I can understand a little but cannot communicate at all"),
    Pre_Intermediate("I can understand and communicate a little"),
    Intermediate("I can understand well, but cannot communicate much"),
    Upper_Intermediate("I can understand and communicate well, but sometimes a bit slow"),
    Advanced("I can understand and communicate without any problems"),
    Native("I understand and communicate as a native");

    private String description;

    LanguageLevel(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
