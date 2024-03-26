package com.mmf.resumeBuilder.service.wordtools;

import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;

public class ClassicAccountingDocumentGenerator implements DocumentGenerator {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    private static final String TITLE_COLOR = "262626";
    private static final String TITLE_FONT_FAMILY = "Speak Pro (Headings)";

    @Override
    public void generateWordDocument(Resume resume) {
        try {
            XWPFDocument document = new XWPFDocument();

//            PersonalInformation personalInformation = resume.getPersonalInformation();
//
//            if (personalInformation != null) {
//                createNameTitle(document, personalInformation);

//            XWPFTable table = createTable(document);
            XWPFTable table = createTable(document);
            if (!table.getRows().isEmpty())
                table.removeRow(0);

            String[] rowData1 = {"Row 1, Col 1", "Row 1, Col 2"};
            addRowWithColumns(table, rowData1);

            FileOutputStream out = new FileOutputStream(STORE_PATH + "Test.docx");
            document.write(out);
            out.close();
            document.close();
//            } else {
//                throw new IOException("Contact information can not be empty");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createNameTitle(XWPFDocument document, PersonalInformation personalInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        FontProperties fontProperties = new FontProperties(35, TITLE_COLOR, TITLE_FONT_FAMILY);
        createTitleRun(paragraph, personalInformation.getFullName(), fontProperties);
    }

    private static void addContactInformation(XWPFDocument document, List<ContactMethod> contactInformation) {
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistance) {
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
    }

    private static String generateFilePath(PersonalInformation personalInformation) {
        return STORE_PATH +
                personalInformation.getFullName() +
                " _ " +
                LocalDate.now() +
                " _ " +
                LocalTime.now().getHour() + "-" + LocalTime.now().getMinute() + "-" + LocalTime.now().getSecond() +
                ".docx";
    }
}
