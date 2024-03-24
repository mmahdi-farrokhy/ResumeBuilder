package com.mmf.resumeBuilder.service.wordtools;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordProcessing {
    public static final String TITLE_COLOR = "262626";
    public static final String BODY_COLOR = "5A5A5A";
    public static final int BODY_SIZE = 10;
    public static final String BULLET_COLOR = "D195A9";
    public static final int TITLE_SIZE = 16;
    private static final String BODY_FONT_FAMILY = "Times New Roman (Headings CS)";
    public static final String TITLE_FONT_FAMILY = "Speak Pro (Headings)";

    public static void addBulletToParagraph(XWPFParagraph paragraph, int size, String hexColor) {
        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" • ");
        bulletRun.setFontSize(size);
        bulletRun.setColor(hexColor);
    }

    public static void addDashToParagraph(XWPFParagraph paragraph, int size, String hexColor) {
        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" - ");
        bulletRun.setFontSize(size);
        bulletRun.setColor(hexColor);
    }

    public static void createBodyRun(XWPFParagraph paragraph, String text, boolean bold) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(BODY_SIZE);
        run.setColor(BODY_COLOR);
        run.setFontFamily(BODY_FONT_FAMILY);
        run.setText(text);
        run.setBold(bold);
    }

    public static void createTitleRun(XWPFParagraph paragraph, String text, int fontSize) {
        paragraph.getCTP().getPPr().addNewShd().setFill("F6EAEE");
        XWPFRun run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setColor(TITLE_COLOR);
        run.setFontFamily(TITLE_FONT_FAMILY);
        run.setText(text);
    }
}
