package com.mmf.resumeBuilder.enums.hardskill;

public enum CRM implements IHardSkillType {
    Microsoft_Dynamics("Microsoft Dynamics"),
    Sales_Force("Sales Force"),
    Sugar("Sugar"),
    Didar("دیدار"),
    Sarv("سرو"),
    Payam_Gostar("پیام گستر"),
    Ravesh("روش"),
    Ideal("ایده آل"),
    Dana("Dana");

    private String persianTitle;

    CRM(String persianTitle) {
        this.persianTitle = persianTitle;
    }
}
