package com.mmf.resumeBuilder.enums.language;

import lombok.Getter;

@Getter
public enum LanguageLevel {
    BASIC("I can understand a little but cannot communicate at all"),
    PRE_INTERMEDIATE("I can understand and communicate a little"),
    INTERMEDIATE("I can understand well, but cannot communicate much"),
    UPPER_INTERMEDIATE("I can understand and communicate well, but sometimes a bit slow"),
    ADVANCED("I can understand and communicate without any problems"),
    NATIVE("I understand and communicate as a native");

    private String description;

    LanguageLevel(String description) {
        this.description = description;
    }
}
