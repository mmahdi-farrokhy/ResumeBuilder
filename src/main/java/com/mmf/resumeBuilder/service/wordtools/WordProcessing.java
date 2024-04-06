package com.mmf.resumeBuilder.service.wordtools;

import com.mmf.resumeBuilder.entity.resume.PersonalInformation;
import com.mmf.resumeBuilder.service.wordtools.documentgenerator.SimpleFloristDocumentGenerator;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class WordProcessing {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";

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

    public static void addSymbolToParagraph(XWPFParagraph paragraph, Symbol symbol, int size) {
        XWPFRun symbolRun = paragraph.createRun();
        symbolRun.setFontSize(size);
        symbolRun.setColor(symbol.color());

        if (symbol.type() == ':')
            symbolRun.setText(symbol.type() + " ");
        else
            symbolRun.setText(" " + symbol.type() + " ");
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

    public static void insertNewLine(XWPFTableCell cell) {
        cell.addParagraph();
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

    public static String generateFilePath(PersonalInformation personalInformation, String themeName) {
        return STORE_PATH +
                themeName + " - " +
                personalInformation.getFullName() +
                " - " +
                LocalDate.now().getYear() + "_" + LocalDate.now().getMonth() + "_" + LocalDate.now().getDayOfMonth() + "_" +
                " - " +
                LocalTime.now().getHour() + "_" + LocalTime.now().getMinute() + "_" + LocalTime.now().getSecond() +
                ".docx";
    }

    public static XWPFTableRow createRow(XWPFTable table) {
        XWPFTableRow row = table.createRow();
        if (!row.getTableCells().isEmpty()) {
            row.getTableCells().clear();
        }

        return row;
    }

    public static XWPFTableCell createCell(XWPFTableRow row, String width) {
        XWPFTableCell cell = row.createCell();
        cell.setWidth(width);
        if (!cell.getParagraphs().isEmpty())
            cell.removeParagraph(0);

        return cell;
    }

    public static void insertEmptyRow(XWPFTable table) {
        table.createRow();
    }

    public static XWPFTableCell getRowCell(XWPFTableRow row, int paragraphIndex) {
        XWPFTableCell cell = row.getTableCells().get(paragraphIndex);
        cell.setWidth(SimpleFloristDocumentGenerator.TWO_INCH_WIDTH);

        if (!cell.getParagraphs().isEmpty()) {
            for (int paragraphNumber = 0; paragraphNumber < cell.getParagraphs().size(); paragraphNumber++) {
                cell.removeParagraph(0);
            }
        }

        return cell;
    }

    public static void writeInTableCell(XWPFTableCell cell, String text, FontProperties font, boolean bold) {
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        addRunToParagraph(paragraph, text, font, bold);
    }

    public static void writeInTableCell(XWPFParagraph paragraph, List<String> textParts, FontProperties font, Symbol symbol, boolean bold) {
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        int partNumber = 0;
        for (String textPart : textParts) {
            partNumber++;
            addRunToParagraph(paragraph, textPart, font, bold);

            if (partNumber < textParts.size())
                addSymbolToParagraph(paragraph, symbol, font.getSize());
        }
    }

    public static void writeHyperlinkInTableCell(XWPFParagraph paragraph, Map<String, String> textParts, FontProperties font, Symbol symbol, boolean bold) {
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        int partNumber = 0;
        for (Map.Entry<String, String> textPart : textParts.entrySet()) {
            partNumber++;
            addHyperlinkRunToParagraph(paragraph, textPart.getValue(), textPart.getKey(), font, bold);

            if (partNumber < textParts.size())
                addSymbolToParagraph(paragraph, symbol, font.getSize());
        }
    }
}
