package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.wordtools.WordProcessing;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;

public class DocumentGenerator {

    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    public static final int INDENTATION = 300;

    ///////////////////////////////// Generate word documents using Java code
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

            createSkillsTitle(document, resume.getHardSkills(), resume.getSoftSkills());
            addSkills(document, resume.getHardSkills(), resume.getSoftSkills());

            createEducationTitle(document, resume.getEducations());
            addEducations(document, resume.getEducations());

            createActivitiesTitle(document, resume.getHobbies());
            addActivities(document, resume.getHobbies());

            FileOutputStream out = new FileOutputStream(STORE_PATH + "NameTest.docx");
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addActivities(XWPFDocument document, List<Hobby> hobbies) {
        if (hobbies != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            for (Hobby hobby : hobbies) {
                paragraph.setIndentationLeft(INDENTATION);
                createBodyRun(paragraph, hobby.getTitle(), false);
                addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
            }

            paragraph.createRun().addCarriageReturn();
        }
    }

    private static void createActivitiesTitle(XWPFDocument document, List<Hobby> hobbies) {
        if (hobbies != null && !hobbies.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            WordProcessing.createTitleRun(paragraph, "Activities", 16);
        }
    }

    private static void addEducations(XWPFDocument document, List<Education> educations) {
        if (educations != null) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        for (Education education : educations) {
            paragraph.setIndentationLeft(INDENTATION);
            createBodyRun(paragraph, extractEducationDuration(education), false);
            paragraph.createRun().addCarriageReturn();
            createBodyRun(paragraph, extractEducationTitle(education), true);
            paragraph.createRun().addCarriageReturn();
        }

        paragraph.createRun().addCarriageReturn();
    }}

    private static void createEducationTitle(XWPFDocument document, List<Education> educations) {
        if (educations != null && !educations.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            WordProcessing.createTitleRun(paragraph, "Education", 16);
        }
    }

    private static void addSkills(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        if (hardSkills != null || softSkills != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);

            if (hardSkills != null) {
                for (HardSkill hardSkill : hardSkills) {
                    paragraph.setIndentationLeft(INDENTATION);
                    createBodyRun(paragraph, hardSkill.getType().toString(), false);
                    addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                }
            }

            if (softSkills != null) {
                for (SoftSkill softSkill : softSkills) {
                    paragraph.setIndentationLeft(INDENTATION);
                    createBodyRun(paragraph, softSkill.getTitle(), false);
                    addBulletToParagraph(paragraph, BODY_SIZE, BULLET_COLOR);
                }
            }

            paragraph.createRun().addCarriageReturn();
        }
    }

    private static void createSkillsTitle(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            WordProcessing.createTitleRun(paragraph, "Skills", 16);
        }
    }

    private static void addFormerColleagues(XWPFDocument document, List<FormerColleague> formerColleagues) {
        if (formerColleagues != null) {
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
    }

    private static void createFormerColleaguesTitle(XWPFDocument document, List<FormerColleague> formerColleagues) {
        if (formerColleagues != null && !formerColleagues.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            WordProcessing.createTitleRun(paragraph, "Former Colleagues", 16);
        }
    }

    private static void addJobExperiences(XWPFDocument document, List<JobExperience> jobExperiences) {
        if (jobExperiences != null) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            paragraph.setIndentationLeft(INDENTATION);

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
    }

    private static void createExperiencesTitle(XWPFDocument document, List<JobExperience> jobExperiences) {
        if (jobExperiences != null && !jobExperiences.isEmpty()) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            WordProcessing.createTitleRun(paragraph, "Experiences", 16);
        }
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        createBodyRun(paragraph, summary.getText(), true);
        paragraph.createRun().addCarriageReturn();
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

    private static void createNameTitle(XWPFDocument document, Resume resume) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WordProcessing.createTitleRun(paragraph, resume.getPersonalInformation().getFullName(), 35);
    }

    private static String extractSkillsTitle(List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        return (hardSkills == null || hardSkills.isEmpty()) && (softSkills == null || softSkills.isEmpty()) ? "" : "Skills";
    }

    private static String extractEducationTitle(Education education) {
        return education.getMajor() + " | " + education.getUniversity() + " | " + education.getDegreeLevel();
    }

    private static String extractEducationDuration(Education education) {
        String startDate = String.valueOf(education.getStartYear());
        String endDate = education.getEndYear() == 0 ? "Today" : String.valueOf(education.getStartYear());
        return startDate + " - " + endDate;
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
}
