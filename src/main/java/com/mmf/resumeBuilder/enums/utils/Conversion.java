package com.mmf.resumeBuilder.enums.utils;

public class Conversion {

    public static String removeUnderScores(Enum<?> enumValue) {
        return enumValue.name().replaceAll("_", " ");
    }
}
