package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.entity.resume.Resume;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DocumentGenerator {
    public static void generateResumeDocument(Resume resume, String filePath) {
        try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(filePath)) {

            // Check and include each field if it is present and non-empty
            addSection(document, "Personal Information", resume.getPersonalInformation().toString());
            addListSection(document, "Contact Information", resume.getContactInformation());
            addSection(document, "Summary", resume.getSummary().toString());
            addListSection(document, "Education", resume.getEducations());
            // Add more sections for other fields in a similar manner

            // Save the Word document
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addSection(XWPFDocument document, String title, String content) {
        if (content != null && !content.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setText(title);
            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText(content);
        }
    }

    private static <T> void addListSection(XWPFDocument document, String title, List<T> items) {
        if (items != null && !items.isEmpty()) {
            addSection(document, title, "");
            for (T item : items) {
                addSection(document, "- ", item.toString());
            }
        }
    }
}
