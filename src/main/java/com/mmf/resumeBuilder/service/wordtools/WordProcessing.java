package com.mmf.resumeBuilder.service.wordtools;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.math.BigInteger;
import java.util.List;

public class WordProcessing {

    public static XWPFDocument createDocument(double top, double bottom, double left, double right) {
        XWPFDocument document = new XWPFDocument();
        setMargins(document, top, bottom, left, right);
        return document;
    }

    public static void setMargins(XWPFDocument document, double topInch, double bottomInch, double leftInch, double rightInch) {
        CTPageMar pageMar = document.getDocument().getBody().addNewSectPr().addNewPgMar();
        int topTWIPs = inchToTWIPs(topInch);
        int bottomWIPs = inchToTWIPs(bottomInch);
        int leftTWIPs = inchToTWIPs(leftInch);
        int rightTWIPs = inchToTWIPs(rightInch);

        pageMar.setTop(BigInteger.valueOf(topTWIPs));
        pageMar.setBottom(BigInteger.valueOf(bottomWIPs));
        pageMar.setLeft(BigInteger.valueOf(leftTWIPs));
        pageMar.setRight(BigInteger.valueOf(rightTWIPs));
    }

    private static int inchToTWIPs(double inch) {
        return (int) (inch * 1440);
    }

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

    public static void createBodyRun(XWPFParagraph paragraph, String text, FontProperties font) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(font.getSize());
        run.setColor(font.getColor());
        run.setFontFamily(font.getFamily());
        run.setText(text);
    }

    public static void createBoldBodyRun(XWPFParagraph paragraph, String text, FontProperties font) {
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(font.getSize());
        run.setColor(font.getColor());
        run.setFontFamily(font.getFamily());
        run.setText(text);
    }

    public static void createHyperlinkRun(XWPFParagraph paragraph, String uri, String description, FontProperties font, boolean bold) {
        String rId = paragraph
                .getDocument()
                .getPackagePart()
                .addExternalRelationship(uri, XWPFRelation.HYPERLINK.getRelation())
                .getId();

        CTHyperlink cthyperLink = paragraph.getCTP().addNewHyperlink();
        cthyperLink.setId(rId);
        cthyperLink.addNewR();

        XWPFHyperlinkRun hyperlinkRun = new XWPFHyperlinkRun(
                cthyperLink,
                cthyperLink.getRArray(0),
                paragraph);

        hyperlinkRun.setText(description);
        hyperlinkRun.setColor(font.getColor());
        hyperlinkRun.setFontSize(font.getSize());
        hyperlinkRun.setFontFamily(font.getFamily());
        hyperlinkRun.setUnderline(UnderlinePatterns.SINGLE);
        hyperlinkRun.setBold(bold);
    }

    public static void createTitleRun(XWPFParagraph paragraph, String text, FontProperties font) {
        paragraph.getCTP().getPPr().addNewShd().setFill("F6EAEE");
        XWPFRun run = paragraph.createRun();
        run.setFontSize(font.getSize());
        run.setColor(font.getColor());
        run.setFontFamily(font.getFamily());
        run.setText(text);
    }

    public static void insertNewLine(XWPFParagraph paragraph) {
        paragraph.createRun().addCarriageReturn();
    }

    public static void addRowToTable(XWPFDocument document, List<String> rowData, FontProperties font, Boolean bold, int indentation) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int pageWidthTwips = 12240;
        int columnCounter = rowData.size();
        int columnWidths = pageWidthTwips / columnCounter;

        for (String currentRow : rowData) {
            XWPFTableCell cell = row.createCell();
            CTTblWidth width = CTTblWidth.Factory.newInstance();
            width.setW(columnWidths);
            width.setType(STTblWidth.DXA);

            if (cell.getCTTc().getTcPr() == null) {
                cell.getCTTc().addNewTcPr();
            }

            cell.getCTTc().getTcPr().setTcW(width);
            if (!cell.getParagraphs().isEmpty())
                cell.removeParagraph(0);

            for (String textBlock : currentRow.split("\\n")) {
                XWPFParagraph paragraph = cell.addParagraph();
                paragraph.setIndentationLeft(indentation);

                if (bold) {
                    createBoldBodyRun(paragraph, textBlock, font);
                } else {
                    createBodyRun(paragraph, textBlock, font);
                }
            }
        }
    }

    public static void addRowToTableDelimitedBySymbol(XWPFDocument document, List<String> rowData, FontProperties font, Boolean bold, int indentation, String symbol, String symbolColor) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;

        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationLeft(indentation);

        int dataNumber = 0;
        for (String rowDatum : rowData) {
            dataNumber++;
            for (String textBlock : rowDatum.split("\\n")) {

                if (bold) {
                    createBoldBodyRun(paragraph, textBlock, font);
                } else {
                    createBodyRun(paragraph, textBlock, font);
                }

                if (dataNumber < rowData.size()) {
                    XWPFRun bulletRun = paragraph.createRun();
                    bulletRun.setText(symbol);
                    bulletRun.setFontSize(font.getSize());
                    bulletRun.setColor(symbolColor);
                    bulletRun.setFontFamily(font.getFamily());
                    bulletRun.setBold(bold);
                }
            }
        }
    }

    public static void addDashedRowToTableDelimitedBySymbol(XWPFDocument document, List<String> rowData, FontProperties font, boolean bold, int indentation, String symbol, String symbolColor) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;

        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationLeft(indentation);

        XWPFRun dashRun = paragraph.createRun();
        dashRun.setText(" - ");
        dashRun.setFontSize(font.getSize());
        dashRun.setFontFamily(font.getFamily());
        dashRun.setColor(symbolColor);
        dashRun.setBold(bold);

        int dataNumber = 0;
        for (String rowDatum : rowData) {
            dataNumber++;
            for (String textBlock : rowDatum.split("\\n")) {


                if (bold) {
                    createBoldBodyRun(paragraph, textBlock, font);
                } else {
                    createBodyRun(paragraph, textBlock, font);
                }

                if (dataNumber < rowData.size()) {
                    XWPFRun symbolRun = paragraph.createRun();
                    symbolRun.setText(symbol);
                    symbolRun.setFontSize(font.getSize());
                    symbolRun.setColor(symbolColor);
                    symbolRun.setFontFamily(font.getFamily());
                    symbolRun.setBold(bold);
                }
            }
        }
    }

    public static void addDashedRowToTable(XWPFDocument document, List<String> rowData, FontProperties font, String dashColor, boolean bold, int indentation) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int pageWidthTwips = 12240;
        int columnCounter = rowData.size();
        int columnWidths = pageWidthTwips / columnCounter;

        for (String currentRow : rowData) {
            XWPFTableCell cell = row.createCell();
            CTTblWidth width = CTTblWidth.Factory.newInstance();
            width.setW(columnWidths);
            width.setType(STTblWidth.DXA);

            if (cell.getCTTc().getTcPr() == null) {
                cell.getCTTc().addNewTcPr();
            }

            cell.getCTTc().getTcPr().setTcW(width);
            if (!cell.getParagraphs().isEmpty())
                cell.removeParagraph(0);

            for (String textBlock : currentRow.split("\\n")) {
                XWPFParagraph paragraph = cell.addParagraph();
                paragraph.setIndentationLeft(indentation);

                XWPFRun bulletRun = paragraph.createRun();
                bulletRun.setText(" - ");
                bulletRun.setFontSize(font.getSize());
                bulletRun.setFontFamily(font.getFamily());
                bulletRun.setColor(dashColor);
                bulletRun.setBold(bold);

                if (bold) {
                    createBoldBodyRun(paragraph, textBlock, font);
                } else {
                    createBodyRun(paragraph, textBlock, font);
                }
            }
        }
    }

    public static void addHyperlinkRowToTable(XWPFDocument document, String uri, String description, FontProperties font, boolean bold, int indentation) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;
        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationLeft(indentation);
        createHyperlinkRun(paragraph, uri, description, font, bold);
    }

    public static void addDashedHyperlinkRowToTable(XWPFDocument document, String uri, String description, FontProperties font, String dashColor, boolean bold, int indentation) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;
        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" - ");
        bulletRun.setFontSize(font.getSize());
        bulletRun.setFontFamily(font.getFamily());
        bulletRun.setColor(dashColor);
        bulletRun.setBold(bold);

        paragraph.setIndentationLeft(indentation);
        createHyperlinkRun(paragraph, uri, description, font, bold);
    }

    public static void addHyperlinkRowToTableDelimitedBySymbol(XWPFDocument document, String uri, List<String> description, FontProperties font, boolean bold, int indentation, String symbol, String symbolColor) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;

        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationLeft(indentation);

        int dataNumber = 0;
        for (String descriptionPart : description) {
            dataNumber++;
            createHyperlinkRun(paragraph, uri, descriptionPart, font, bold);

            if (dataNumber < description.size()) {
                XWPFRun symbolRun = paragraph.createRun();
                symbolRun.setText(symbol);
                symbolRun.setFontSize(font.getSize());
                symbolRun.setColor(symbolColor);
                symbolRun.setFontFamily(font.getFamily());
                symbolRun.setBold(bold);
            }
        }
    }

    public static void addDashedHyperlinkRowToTableDelimitedBySymbol(XWPFDocument document, String uri, List<String> description, FontProperties font, boolean bold, int indentation, String symbol, String symbolColor) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int columnWidths = 12240;

        XWPFTableCell cell = row.createCell();
        CTTblWidth width = CTTblWidth.Factory.newInstance();
        width.setW(columnWidths);
        width.setType(STTblWidth.DXA);

        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        cell.getCTTc().getTcPr().setTcW(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationLeft(indentation);

        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" - ");
        bulletRun.setFontSize(font.getSize());
        bulletRun.setFontFamily(font.getFamily());
        bulletRun.setColor(symbolColor);
        bulletRun.setBold(bold);

        int dataNumber = 0;
        for (String descriptionPart : description) {
            dataNumber++;
            createHyperlinkRun(paragraph, uri, descriptionPart, font, bold);

            if (dataNumber < description.size()) {
                XWPFRun symbolRun = paragraph.createRun();
                symbolRun.setText(symbol);
                symbolRun.setFontSize(font.getSize());
                symbolRun.setColor(symbolColor);
                symbolRun.setFontFamily(font.getFamily());
                symbolRun.setBold(bold);
            }
        }
    }

    public static XWPFTable createTable(XWPFDocument document) {
        XWPFTable table = document.createTable();
        if (!table.getRows().isEmpty())
            table.removeRow(0);

        table.getCTTbl().getTblPr().unsetTblBorders();
        return table;
    }
}
