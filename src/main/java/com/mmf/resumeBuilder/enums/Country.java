package com.mmf.resumeBuilder.enums;

import lombok.Getter;

@Getter
public enum Country {
    AFGHANISTAN(1, IsoCode.AF),
    ARGENTINA(2, IsoCode.AR),
    ARMENIA(3, IsoCode.AM),
    AUSTRALIA(4, IsoCode.AU),
    AUSTRIA(5, IsoCode.AT),
    AZERBAIJAN(6, IsoCode.AZ),
    BELGIUM(7, IsoCode.BE),
    BRAZIL(8, IsoCode.BR),
    CANADA(9, IsoCode.CA),
    CHINA(10, IsoCode.CN),
    FRANCE(11, IsoCode.FR),
    GERMANY(12, IsoCode.DE),
    HONG_KONG(13, IsoCode.HK),
    HUNGARY(14, IsoCode.HU),
    INDIA(15, IsoCode.IS),
    IRAN(16, IsoCode.IR),
    IRAQ(17, IsoCode.IQ),
    ITALY(18, IsoCode.IT),
    JAPAN(19, IsoCode.JP),
    JORDAN(20, IsoCode.JO),
    KAZAKHSTAN(21, IsoCode.KZ),
    KUWAIT(22, IsoCode.KW),
    MEXICO(23, IsoCode.MX),
    NETHERLANDS(24, IsoCode.NL),
    NORWAY(25, IsoCode.NO),
    OMAN(26, IsoCode.OM),
    PAKISTAN(27, IsoCode.PK),
    POLAND(28, IsoCode.PL),
    PORTUGAL(29, IsoCode.PT),
    QATAR(30, IsoCode.QA),
    SOUTH_AFRICA(31, IsoCode.ZA),
    SPAIN(32, IsoCode.ES),
    SWEDEN(33, IsoCode.SE),
    SWITZERLAND(34, IsoCode.CH),
    TAJIKISTAN(35, IsoCode.TJ),
    TURKEY(36, IsoCode.TR),
    TURKMENISTAN(37, IsoCode.TM),
    UNITED_ARAB_EMIRATES(38, IsoCode.AE),
    UNITED_KINGDOM(39, IsoCode.GB),
    UNITED_STATES(40, IsoCode.US),
    RUSSIA(41, IsoCode.RU),
    SYRIA(42, IsoCode.SY);

    private final int id;
    private IsoCode isoCode;

    Country(int id, IsoCode isoCode) {
        this.id = id;
    }
}
