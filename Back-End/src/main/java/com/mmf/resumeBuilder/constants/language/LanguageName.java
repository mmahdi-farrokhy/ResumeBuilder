package com.mmf.resumeBuilder.constants.language;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum LanguageName {
    Persian,
    English,
    Arabic,
    German,
    French,
    Spanish,
    Turkish,
    Russian,
    Portuguese,
    Italian,
    Japanese,
    Korean,
    Chinese,
    Armenian,
    Dutch,
    Greek,
    Hindi,
    Kurdish;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}