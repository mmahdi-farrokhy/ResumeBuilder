package com.mmf.resumeBuilder.enums.hardskill;

import lombok.Getter;

@Getter
public enum AccountingAndFinancialSoftware implements HardSkillType {
    Hamkaran_System("همکاران سیستم"),
    Rayvarz("رایورز"),
    Tadbir("تدبیر"),
    Holoo("هلو"),
    Shygun("شایگان"),
    Rafe("رافع"),
    Mizan("میزان"),
    Sepidar("سپیدار"),
    Seyagh("sdhr"),
    Vision("ویژن"),
    Fanavaran("فنآوران(صدور بیمه نامه)"),
    Shomaran_System("شماران سیستم"),
    Rahkaran("راهکاران"),
    Rayan_Hamafza("رایان هم افزار"),
    Payvast("پیوست"),
    Varanegar("ورا نگر"),
    Sepidz("سپیدز");
    
    private String persianTitle;

    AccountingAndFinancialSoftware(String persianTitle) {
        this.persianTitle = persianTitle;
    }
}
