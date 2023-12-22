package com.mmf.resumeBuilder.enums.location;

import com.mmf.resumeBuilder.enums.utils.Conversion;

public enum IsoCode {
    AF,
    AL,
    DZ,
    AR,
    AM,
    AU,
    AT,
    AZ,
    BD,
    BY,
    BE,
    BA,
    BR,
    BG,
    BF,
    KH,
    CM,
    CA,
    CL,
    CN,
    CO,
    CR,
    HR,
    CU,
    CZ,
    DK,
    EC,
    EG,
    EE,
    FI,
    FR,
    GE,
    DE,
    GR,
    HK,
    HU,
    IS,
    IN,
    ID,
    IR,
    IQ,
    IE,
    IT,
    JP,
    JO,
    KZ,
    KW,
    LY,
    MG,
    MY,
    MX,
    MA,
    NL,
    NG,
    NO,
    OM,
    PK,
    PH,
    PL,
    PT,
    QA,
    RO,
    SA,
    SN,
    RS,
    SG,
    SK,
    SI,
    ZA,
    ES,
    LK,
    SE,
    CH,
    TJ,
    TH,
    TN,
    TR,
    TM,
    UA,
    AE,
    GB,
    US,
    UZ,
    VN,
    YE,
    RU,
    PS,
    SY;

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
