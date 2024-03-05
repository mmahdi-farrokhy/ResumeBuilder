package com.mmf.resumeBuilder.constants.location;

import com.mmf.resumeBuilder.constants.utils.Conversion;
import lombok.Getter;

@Getter
public enum Country {
    Afghanistan(1, IsoCode.AF),
    Argentina(2, IsoCode.AR),
    Armenia(3, IsoCode.AM),
    Australia(4, IsoCode.AU),
    Austria(5, IsoCode.AT),
    Azerbaijan(6, IsoCode.AZ),
    Belgium(7, IsoCode.BE),
    Brazil(8, IsoCode.BR),
    Canada(9, IsoCode.CA),
    China(10, IsoCode.CN),
    France(11, IsoCode.FR),
    Germany(12, IsoCode.DE),
    Hong_kong(13, IsoCode.HK),
    Hungary(14, IsoCode.HU),
    India(15, IsoCode.IS),
    Iran(16, IsoCode.IR),
    Iraq(17, IsoCode.IQ),
    Italy(18, IsoCode.IT),
    Japan(19, IsoCode.JP),
    Jordan(20, IsoCode.JO),
    Kazakhstan(21, IsoCode.KZ),
    Kuwait(22, IsoCode.KW),
    Mexico(23, IsoCode.MX),
    Netherlands(24, IsoCode.NL),
    Norway(25, IsoCode.NO),
    Oman(26, IsoCode.OM),
    Pakistan(27, IsoCode.PK),
    Poland(28, IsoCode.PL),
    Portugal(29, IsoCode.PT),
    Qatar(30, IsoCode.QA),
    South_Africa(31, IsoCode.ZA),
    Spain(32, IsoCode.ES),
    Sweden(33, IsoCode.SE),
    Switzerland(34, IsoCode.CH),
    Tajikistan(35, IsoCode.TJ),
    Turkey(36, IsoCode.TR),
    Turkmenistan(37, IsoCode.TM),
    United_Arab_Emirates(38, IsoCode.AE),
    United_Kingdom(39, IsoCode.GB),
    United_States(40, IsoCode.US),
    Russia(41, IsoCode.RU),
    Syria(42, IsoCode.SY);

    private final int id;
    private IsoCode isoCode;

    Country(int id, IsoCode isoCode) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Conversion.removeUnderScores(this);
    }
}
