package com.mmf.resumeBuilder.constants.contactinformation;

import com.mmf.resumeBuilder.constants.utils.Conversion;

public enum ContactType {
    Phone_Number,
    Email,
    LinkedIn,
    GitHub,
    Twitter,
    Facebook,
    Instagram,
    Telegram,
    Dribbble,
    WhatsApp,
    Skype,
    YouTube,
    StackOverflow,
    Figma,
    Pinterest,
    GitLab,
    Address;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
