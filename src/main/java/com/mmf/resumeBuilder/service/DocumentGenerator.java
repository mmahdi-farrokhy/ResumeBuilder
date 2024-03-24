package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class DocumentGenerator {

    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final int BODY_SIZE = 10;
    public static final String BODY_COLOR = "5A5A5A";
    public static final String BULLET_COLOR = "D195A9";
    private static final String BODY_FONT_FAMILY = "Times New Roman (Headings CS)";
    public static final int TITLE_SIZE = 16;
    public static final String TITLE_COLOR = "262626";
    private static final String TITLE_FONT_FAMILY = "Speak Pro (Headings)";

    public static void createNewWordDocument(Resume resume) {
        try {
            XWPFDocument document = new XWPFDocument();

            createNameTitle(document, resume);
            addContactInformation(document, resume.getContactInformation());
            addSummary(document, resume.getSummary());
            createExperiencesTitle(document, resume.getJobExperiences());
            addJobExperiences(document, resume.getJobExperiences());
            createFormerColleaguesTitle(document, resume.getFormerColleagues());
            addFormerColleagues(document, resume.getFormerColleagues());

            FileOutputStream out = new FileOutputStream(STORE_PATH + "NameTest.docx");
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFormerColleagues(XWPFDocument document, List<FormerColleague> formerColleagues) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        for (FormerColleague formerColleague : formerColleagues) {
            paragraph.setIndentationLeft(300);
            addDashToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(paragraph, formerColleague.getFullName(), false);
            addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(paragraph, formerColleague.getPosition(), false);
            addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            createBodyRun(paragraph, formerColleague.getPhoneNumber(), false);
            paragraph.createRun().addCarriageReturn();
        }
    }

    private static void createFormerColleaguesTitle(XWPFDocument document, List<FormerColleague> formerColleagues) {
        if (!formerColleagues.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Former Colleagues", 16);
        }
    }

    private static void addJobExperiences(XWPFDocument document, List<JobExperience> jobExperiences) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        for (JobExperience jobExperience : jobExperiences) {
            createBodyRun(paragraph, extractJobDuration(jobExperience), false);
            paragraph.createRun().addCarriageReturn();
            createBodyRun(paragraph, extractJobTitle(jobExperience), true);
            paragraph.createRun().addCarriageReturn();
            createBodyRun(paragraph, extractJobDescription(jobExperience), false);
            paragraph.createRun().addCarriageReturn();
            paragraph.createRun().addCarriageReturn();
        }
    }

    private static void createExperiencesTitle(XWPFDocument document, List<JobExperience> jobExperiences) {
        if (!jobExperiences.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            createTitleRun(paragraph, "Experiences", 16);
        }
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = createBodyRun(paragraph, summary.getText(), false);
        run.setBold(true);
        run.addCarriageReturn();
        run.addCarriageReturn();
    }

    private static void addContactInformation(XWPFDocument document, List<ContactMethod> contactInformation) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);

        for (ContactMethod contactMethod : contactInformation) {
            createBodyRun(paragraph, contactMethod.getContent(), false);
            addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
        }

        paragraph.createRun().addCarriageReturn();
    }

    private static void addBulletToParagraph(XWPFParagraph paragraph, int size, String hexColor) {
        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" • ");
        bulletRun.setFontSize(size);
        bulletRun.setColor(hexColor);
    }

    private static void addDashToParagraph(XWPFParagraph paragraph, int size, String hexColor) {
        XWPFRun bulletRun = paragraph.createRun();
        bulletRun.setText(" - ");
        bulletRun.setFontSize(size);
        bulletRun.setColor(hexColor);
    }

    private static void createNameTitle(XWPFDocument document, Resume resume) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        createTitleRun(paragraph, resume.getPersonalInformation().getFullName(), 35);
    }

    public static void generateDocument(Resume resume) {
        // Copy the template file to the output path
        String destinationFileName = generateFilePath(resume);
        Path destinationPath = Paths.get(destinationFileName);
        try {
            Path renamedPath = destinationPath.resolve(destinationFileName);
            Files.move(destinationPath, renamedPath);

            // Load the copied template file
            try (FileInputStream fis = new FileInputStream(destinationFileName);
                 XWPFDocument doc = new XWPFDocument(fis);
                 FileOutputStream fos = new FileOutputStream(destinationFileName)) {
                List<IBodyElement> bodyElements = doc.getBodyElements();
                IBodyElement iBodyElement = bodyElements.get(9);
                List<XWPFRun> runs = iBodyElement.getBody().getParagraphs().stream().findFirst().get().getRuns();
                //.getBody().getBodyElements().get(6);
//                        stream().findFirst().get().getBody().getBodyElements().

                for (XWPFParagraph paragraph : doc.getParagraphs()) {
                    for (int runNumber = 0; runNumber < paragraph.getRuns().size(); runNumber++) {
//                    for (XWPFRun run : paragraph.getRuns()) {
                        XWPFRun run = paragraph.getRuns().get(runNumber);
                        String text = run.getText(0);
                        if (text != null) {
                            text = text.replace("«Name»", resume.getPersonalInformation().getFullName());
                            text = text.replace("«Address»", extractContactMethod(resume.getContactInformation(), ContactType.Address));
                            text = text.replace("«Phone»", extractContactMethod(resume.getContactInformation(), ContactType.Phone_Number));
                            text = text.replace("«Email»", extractContactMethod(resume.getContactInformation(), ContactType.Email));
                            text = text.replace("«Linkedin»", extractContactMethod(resume.getContactInformation(), ContactType.LinkedIn));
                            XWPFRun newRun = paragraph.insertNewRun(runNumber + 1);
                            newRun.setText("• ");
                            newRun.setColor("F6EAEE"); // Set color R: 246, G: 234, B: 238
                            text = text.replace("Summary", resume.getSummary().getText());
                            text = text.replace("«ExperienceTitle»", "Experiences");
                            text = text.replace("JobDate", extractJobDuration(resume.getJobExperiences().getFirst()));
                            text = text.replace("JobTitle", extractJobTitle(resume.getJobExperiences().getFirst()));
                            text = text.replace("JobDescription", extractJobDescription(resume.getJobExperiences().getFirst()));

                            for (FormerColleague formerColleague : resume.getFormerColleagues())
                                text = text.replace("FormerColleaguesTitle", formerColleague.getFullName());


                            text = text.replace("FormerColleagues", extractFormerColleagues(resume.getFormerColleagues()));
                            text = text.replace("Skills", extractSkillsTitle(resume.getHardSkills(), resume.getSoftSkills()));
                            text = text.replace("Skill", extractSkills(resume.getHardSkills(), resume.getSoftSkills()));
                            text = text.replace("EducationTitle", "Education");
                            text = text.replace("EducationDate", extractEducationDuration(resume.getEducations()));
                            text = text.replace("EducationTitle", extractEducationTitle(resume.getEducations()));
                            run.setText(text, 0);
                            int t = 0;
                        }
                    }
                }

                doc.write(fos);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractSkillsTitle(List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        return (hardSkills == null || hardSkills.isEmpty()) && (softSkills == null || softSkills.isEmpty()) ? "" : "Skills";
    }

    private static String extractEducationTitle(List<Education> educations) {
        Education firstEducation = educations.stream().findFirst().get();
        return firstEducation.getMajor() + " | " + firstEducation.getUniversity() + " | " + firstEducation.getDegreeLevel();
    }

    private static String extractEducationDuration(List<Education> educations) {
        Education firstEducation = educations.stream().findFirst().get();
        return firstEducation.getStartYear() + " - " + firstEducation.getEndYear();
    }

    private static String extractSkills(List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        StringBuilder skillsValue = new StringBuilder();

        for (HardSkill hardSkill : hardSkills) {
            skillsValue.append(hardSkill.getType()).append(" / ");
        }

        for (SoftSkill softSkill : softSkills) {
            skillsValue.append(softSkill.getTitle()).append(" / ");
        }

        return skillsValue.toString();
    }

    private static String extractFormerColleagues(List<FormerColleague> formerColleagues) {
        StringBuilder formerColleaguesValue = new StringBuilder();
        for (FormerColleague formerColleague : formerColleagues) {
            formerColleaguesValue.append("Name: ").append(formerColleague.getFullName()).append(" \\u000B");
            formerColleaguesValue.append("Position: ").append(formerColleague.getPosition());
            formerColleaguesValue.append("Organization: ").append(formerColleague.getOrganizationName());
            formerColleaguesValue.append("Phone Number: ").append(formerColleague.getPhoneNumber());
        }

        return formerColleaguesValue.toString();
    }

    private static String extractJobDescription(JobExperience jobExperience) {
        return jobExperience.getDescription();
    }

    private static String extractJobTitle(JobExperience jobExperience) {
        return jobExperience.getTitle() +
                " | " +
                jobExperience.getCompanyName() +
                " | " +
                jobExperience.getLocation().getCityName();
    }

    private static String extractJobDuration(JobExperience jobExperience) {
        String startDate = (jobExperience.getStartDate().getMonth().toString()).substring(0, 3) + " " + jobExperience.getStartDate().getYear();
        String endDate = jobExperience.getEndDate() == null ? "Today" : jobExperience.getStartDate().getMonth() + " " + jobExperience.getStartDate().getYear();
        return startDate + " - " + endDate;
    }

    private static String extractContactMethod(List<ContactMethod> contactInformation, ContactType contactType) {
        Optional<ContactMethod> contactMethod = contactInformation.stream().filter(c -> c.getType() == contactType).findFirst();
        return contactMethod.isPresent() ? contactMethod.get().getContent() : "";
    }

    private static String generateFilePath(Resume resume) {
        return STORE_PATH +
                resume.getPersonalInformation().getFullName() +
                " _ " +
                LocalDate.now() +
                " _ " +
                LocalTime.now().getHour() + "-" + LocalTime.now().getMinute() + "-" + LocalTime.now().getSecond() +
                ".docx";
    }

    private static XWPFRun createBodyRun(XWPFParagraph paragraph, String text, boolean bold) {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(BODY_SIZE);
        run.setColor(BODY_COLOR);
        run.setFontFamily(BODY_FONT_FAMILY);
        run.setText(text);
        run.setBold(bold);
        return run;
    }

    private static void createTitleRun(XWPFParagraph paragraph, String text, int fontSize) {
        paragraph.getCTP().getPPr().addNewShd().setFill("F6EAEE");
        XWPFRun run = paragraph.createRun();
        run.setFontSize(fontSize);
        run.setColor(TITLE_COLOR);
        run.setFontFamily(TITLE_FONT_FAMILY);
        run.setText(text);
    }
}
