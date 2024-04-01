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
        pageMar.setTop(BigInteger.valueOf((int) (topInch * 1440)));
        pageMar.setBottom(BigInteger.valueOf((int) (bottomInch * 1440)));
        pageMar.setLeft(BigInteger.valueOf((int) (leftInch * 1440)));
        pageMar.setRight(BigInteger.valueOf((int) (rightInch * 1440)));
    }

    public static void addSymbolToParagraph(XWPFParagraph paragraph, int size, String hexColor, char symbol) {
        XWPFRun symbolRun = paragraph.createRun();
        symbolRun.setFontSize(size);
        symbolRun.setColor(hexColor);

        if (symbol == ':')
            symbolRun.setText(symbol + " ");
        else
            symbolRun.setText(" " + symbol + " ");
    }

    public static void addRunToParagraph(XWPFParagraph paragraph, String text, FontProperties font, boolean bold) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(font.getSize());
        run.setColor(font.getColor());
        run.setFontFamily(font.getFamily());
        run.setText(text);
        run.setBold(bold);
    }

    public static void addHyperlinkRunToParagraph(XWPFParagraph paragraph, String uri, String description, FontProperties font, boolean bold) {
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

    public static void addHeadingRunToParagraph(XWPFParagraph paragraph, String text, FontProperties font, String fillColor) {
        if (fillColor != null && !fillColor.isEmpty())
            paragraph.getCTP().getPPr().addNewShd().setFill(fillColor);

        addRunToParagraph(paragraph, text, font, false);
    }

    public static void insertNewLine(XWPFParagraph paragraph) {
        paragraph.createRun().addCarriageReturn();
    }

    public static void addSingleRowTableToDocument(XWPFDocument document, List<String> rowData, FontProperties font, Boolean bold, int indentation) {
        XWPFTable table = createTable(document);
        XWPFTableRow row = table.createRow();

        int pageWidthTWIPs = 12240;
        int columnCounter = rowData.size();
        int columnWidths = pageWidthTWIPs / columnCounter;

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
                addRunToParagraph(paragraph, textBlock, font, bold);
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
