package com.mmf.resumeBuilder.service.wordtools.documentgenerator;

import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.wordtools.FontProperties;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.lang.String.valueOf;

public class SimpleFloristDocumentGenerator implements DocumentGenerator {

    public static final String TWO_INCH_WIDTH = "2900";
    public static final String FIVE_INCH_WIDTH = "14400";
    private static final FontProperties NAME_HEADING_FONT = new FontProperties(26, "5C760A", "Franklin Gothic Medium (Headings)");
    private static final FontProperties HEADING_FONT = new FontProperties(10, "5C760A", "Arial (Body)");
    private static final FontProperties BODY_FONT = new FontProperties(10, "262626", "Arial (Body)");
    private static final FontProperties TITLE_FONT = new FontProperties(10, "000000", "Arial (Body)");
    private static final FontProperties DATE_FONT = new FontProperties(10, "595959", "Arial (Body)");
    private static final String SYMBOL_COLOR = "394a04";

    @Override
    public void generateWordDocument(Resume resume) {
        try {
            XWPFDocument document = createDocument(0.2, 0.3, 0.3, 0.3);

            PersonalInformation personalInformation = resume.getPersonalInformation();
            List<ContactMethod> contactInformation = resume.getContactInformation();
            Summary summary = resume.getSummary();
            List<JobExperience> jobExperiences = resume.getJobExperiences();
            List<FormerColleague> formerColleagues = resume.getFormerColleagues();
            List<HardSkill> hardSkills = resume.getHardSkills();
            List<SoftSkill> softSkills = resume.getSoftSkills();
            List<Course> courses = resume.getCourses();
            List<Project> projects = resume.getProjects();
            List<Education> educations = resume.getEducations();
            List<TeachingAssistance> teachingAssistance = resume.getTeachingAssistance();
            List<Presentation> presentations = resume.getPresentations();
            List<Patent> patents = resume.getPatents();
            List<Research> researches = resume.getResearches();
            List<Language> languages = resume.getLanguages();
            List<Hobby> hobbies = resume.getHobbies();
            List<Membership> memberships = resume.getMemberships();
            List<VolunteerActivity> volunteerActivities = resume.getVolunteerActivities();

            if (personalInformation != null) {
                createNameTitle(document, personalInformation);

                if (contactInformation != null && !contactInformation.isEmpty()) {
                    addContactInformationToDocument(document, contactInformation);
                }

                XWPFTable table = createTable(document);

                if (summary != null) {
                    addSummary(table, summary);
                }

                if (jobExperiences != null && !jobExperiences.isEmpty()) {
                    addJobExperiencesToDocument(table, jobExperiences);
                }

                if (formerColleagues != null && !formerColleagues.isEmpty()) {
                    addFormerColleaguesToDocument(table, formerColleagues);
                }

                if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
                    addSkillsToDocument(table, hardSkills, softSkills);
                }

                if (courses != null && !courses.isEmpty()) {
                    addCoursesToDocument(table, courses);
                }

                if (projects != null && !projects.isEmpty()) {
                    addProjectsToDocument(table, projects);
                }

                if (educations != null && !educations.isEmpty()) {
                    addEducationsToDocument(table, educations);
                }

                if (teachingAssistance != null && !teachingAssistance.isEmpty()) {
                    addTeachingAssistanceToDocument(table, teachingAssistance);
                }

                if (presentations != null && !presentations.isEmpty()) {
                    addPresentationsToDocument(table, presentations);
                }

                if (patents != null && !patents.isEmpty()) {
                    addPatentsToDocument(table, patents);
                }

                if (researches != null && !researches.isEmpty()) {
                    addResearchesToDocument(table, researches);
                }

                if (languages != null && !languages.isEmpty()) {
                    addLanguagesToDocument(table, languages);
                }

                if (hobbies != null && !hobbies.isEmpty()) {
                    addHobbiesToDocument(table, hobbies);
                }

                if (memberships != null && !memberships.isEmpty()) {
                    addMembershipsToDocument(table, memberships);
                }

                if (volunteerActivities != null && !volunteerActivities.isEmpty()) {
                    addVolunteerActivitiesToDocument(table, volunteerActivities);
                }

                FileOutputStream out = new FileOutputStream(STORE_PATH + "\\SF Test.docx");
                document.write(out);
                out.close();
                document.close();
            } else {
                throw new IOException("Contact information can not be empty");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNameTitle(XWPFDocument document, PersonalInformation personalInformation) {
        XWPFParagraph nameParagraph = document.createParagraph();
        nameParagraph.setAlignment(ParagraphAlignment.CENTER);
        addRunToParagraph(nameParagraph, personalInformation.getFullName(), NAME_HEADING_FONT, true);
    }

    private void addContactInformationToDocument(XWPFDocument document, List<ContactMethod> contactInformation) {
        XWPFParagraph contactParagraph = document.createParagraph();
        contactParagraph.setAlignment(ParagraphAlignment.CENTER);

        int methodNumber = 0;
        for (ContactMethod contactMethod : contactInformation) {
            methodNumber++;
            addRunToParagraph(contactParagraph, contactMethod.getContent(), BODY_FONT, false);

            if (methodNumber < contactInformation.size())
                addSymbolToParagraph(contactParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertNewLine(contactParagraph);
    }

    private void addSummary(XWPFTable table, Summary summary) {
        XWPFTableRow row = createRow(table);
        XWPFTableCell cell1 = createCell(row);
        cell1.setWidth(TWO_INCH_WIDTH);
        XWPFParagraph paragraph1 = cell1.addParagraph();
        paragraph1.setAlignment(ParagraphAlignment.LEFT);
        addRunToParagraph(paragraph1, "Summary", HEADING_FONT, true);

        XWPFTableCell cell2 = createCell(row);
        cell2.setWidth(FIVE_INCH_WIDTH);

        for (String summaryParagraph : summary.getText().split("\\n")) {
            XWPFParagraph paragraph2 = cell2.addParagraph();
            paragraph2.setAlignment(ParagraphAlignment.LEFT);
            addRunToParagraph(paragraph2, summaryParagraph, BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    public static void addJobExperiencesToDocument(XWPFTable table, List<JobExperience> jobExperiences) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Experiences", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (JobExperience job : jobExperiences) {
            XWPFParagraph durationParagraph = bodyCell.addParagraph();
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            String jobDuration = calculateDuration(job.getStartDate(), job.getEndDate());
            addRunToParagraph(durationParagraph, jobDuration, DATE_FONT, false);

            addRunToParagraph(titleParagraph, job.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(titleParagraph, job.getCompanyName(), TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(titleParagraph, job.getLocation().getCityName().toString(), TITLE_FONT, true);

            int paragraphNumber = 0;
            String[] split = job.getDescription().split("\\n");
            for (String descriptionBlock : split) {
                paragraphNumber++;
                XWPFParagraph descriptionParagraph = bodyCell.addParagraph();
                addRunToParagraph(descriptionParagraph, descriptionBlock, BODY_FONT, false);

                if (paragraphNumber == split.length)
                    insertNewLine(descriptionParagraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addFormerColleaguesToDocument(XWPFTable table, List<FormerColleague> formerColleagues) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Former Colleagues", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (FormerColleague formerColleague : formerColleagues) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(titleParagraph, formerColleague.getFullName(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(titleParagraph, formerColleague.getPosition(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');

            addRunToParagraph(titleParagraph, formerColleague.getPhoneNumber(), BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    private static void addSkillsToDocument(XWPFTable table, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Skills", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            skills = hardSkills.stream().map(h -> h.getType().toString()).toList();
        }

        if (softSkills != null) {
            List<String> softSkillList = softSkills.stream().map(SoftSkill::getTitle).toList();
            skills = Stream.concat(skills.stream(), softSkillList.stream()).toList();
        }

        int skillNumber = 0;
        XWPFParagraph titleParagraph = bodyCell.addParagraph();
        for (String skill : skills) {
            skillNumber++;

            addRunToParagraph(titleParagraph, skill, BODY_FONT, false);

            if (skillNumber < skills.size())
                addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertEmptyRow(table);
    }

    private static void addCoursesToDocument(XWPFTable table, List<Course> courses) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Courses", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Course course : courses) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(titleParagraph, course.getName(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');
            addRunToParagraph(titleParagraph, course.getInstitute(), BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    private static void addProjectsToDocument(XWPFTable table, List<Project> projects) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Projects", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Project project : projects) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addHyperlinkRunToParagraph(titleParagraph, project.getReferenceLink(), project.getName(), TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());
            addHyperlinkRunToParagraph(titleParagraph, project.getReferenceLink(), projectDuration, TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(titleParagraph, project.getReferenceLink(), project.getStatus().toString(), TITLE_FONT, true);

            int projectNumber = 0;
            String[] split = project.getDescription().split("\\n");
            for (String descriptionBlock : split) {
                projectNumber++;
                XWPFParagraph descriptionParagraph = bodyCell.addParagraph();
                addRunToParagraph(descriptionParagraph, descriptionBlock, BODY_FONT, false);

                if (projectNumber == split.length)
                    insertNewLine(descriptionParagraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addEducationsToDocument(XWPFTable table, List<Education> educations) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Education", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Education education : educations) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            String major = education.getDegreeLevel() + " of " + education.getMajor();
            addRunToParagraph(titleParagraph, major, BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(titleParagraph, education.getUniversity(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            String educationDuration = calculateYearDuration(education.getStartYear(), education.getEndYear());
            addRunToParagraph(titleParagraph, educationDuration, BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    private static void addTeachingAssistanceToDocument(XWPFTable table, List<TeachingAssistance> teachingAssistanceList) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Teaching Assistance", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (TeachingAssistance ta : teachingAssistanceList) {
            XWPFParagraph paragraph = bodyCell.addParagraph();

            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(paragraph, ta.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(paragraph, ta.getUniversity(), BODY_FONT, false);
            addSymbolToParagraph(paragraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            String duration = calculateDuration(ta.getStartDate(), ta.getEndDate());
            addRunToParagraph(paragraph, duration, BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    private static void addPresentationsToDocument(XWPFTable table, List<Presentation> presentations) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Presentations", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Presentation presentation : presentations) {
            XWPFParagraph presentationParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(presentationParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(presentationParagraph, presentation.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(presentationParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(presentationParagraph, presentation.getDate().toString(), TITLE_FONT, true);

            String[] split = presentation.getDescription().split("\\n");
            for (String descriptionParagraph : split) {
                XWPFParagraph paragraph = bodyCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addPatentsToDocument(XWPFTable table, List<Patent> patents) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Patents", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Patent patent : patents) {
            XWPFParagraph presentationParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(presentationParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');

            addRunToParagraph(presentationParagraph, patent.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(presentationParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(presentationParagraph, patent.getRegistrationNumber(), TITLE_FONT, true);
            addSymbolToParagraph(presentationParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');

            addRunToParagraph(presentationParagraph, patent.getRegistrationDate().toString(), TITLE_FONT, true);

            String[] split = patent.getDescription().split("\\n");
            for (String descriptionParagraph : split) {
                XWPFParagraph paragraph = bodyCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);
                insertNewLine(paragraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addResearchesToDocument(XWPFTable table, List<Research> researches) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Researches", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Research research : researches) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '-');

            addHyperlinkRunToParagraph(titleParagraph, research.getReferenceLink(), research.getTitle(), TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(titleParagraph, research.getReferenceLink(), research.getPublisher(), TITLE_FONT, true);
            addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '|');

            addHyperlinkRunToParagraph(titleParagraph, research.getReferenceLink(), research.getDate() + " (Click to open the research)", TITLE_FONT, true);

            int researchNumber = 0;
            String[] split = research.getDescription().split("\\n");
            for (String descriptionBlock : split) {
                researchNumber++;
                XWPFParagraph descriptionParagraph = bodyCell.addParagraph();
                addRunToParagraph(descriptionParagraph, descriptionBlock, BODY_FONT, false);

                if (researchNumber == split.length)
                    insertNewLine(descriptionParagraph);
            }
        }

        insertEmptyRow(table);
    }

    private static void addLanguagesToDocument(XWPFTable table, List<Language> languages) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Languages", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        XWPFParagraph titleParagraph = bodyCell.addParagraph();
        int languageNumber = 0;
        for (Language language : languages) {
            languageNumber++;
            addRunToParagraph(titleParagraph, language.getName().toString(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, ':');

            addRunToParagraph(titleParagraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertEmptyRow(table);
    }

    private static void addHobbiesToDocument(XWPFTable table, List<Hobby> hobbies) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Hobbies", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        XWPFParagraph titleParagraph = bodyCell.addParagraph();
        int hobbyNumber = 0;
        for (Hobby hobby : hobbies) {
            hobbyNumber++;
            addRunToParagraph(titleParagraph, hobby.getTitle(), BODY_FONT, false);

            if (hobbyNumber < hobbies.size())
                addSymbolToParagraph(titleParagraph, TITLE_FONT.getSize(), SYMBOL_COLOR, '•');
        }

        insertEmptyRow(table);
    }

    private static void addMembershipsToDocument(XWPFTable table, List<Membership> memberships) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Memberships", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (Membership membership : memberships) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(titleParagraph, membership.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');
            addRunToParagraph(titleParagraph, membership.getDate().toString(), BODY_FONT, false);
        }

        insertEmptyRow(table);
    }

    private static void addVolunteerActivitiesToDocument(XWPFTable table, List<VolunteerActivity> volunteerActivities) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell headingCell = getRowCell(row, 0);
        XWPFParagraph headingParagraph = headingCell.addParagraph();
        addRunToParagraph(headingParagraph, "Volunteer Activities", HEADING_FONT, true);

        XWPFTableCell bodyCell = getRowCell(row, 1);
        bodyCell.setWidth(FIVE_INCH_WIDTH);

        for (VolunteerActivity activity : volunteerActivities) {
            XWPFParagraph titleParagraph = bodyCell.addParagraph();

            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '-');
            addRunToParagraph(titleParagraph, activity.getTitle(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, BODY_FONT.getSize(), SYMBOL_COLOR, '|');
            addRunToParagraph(titleParagraph, valueOf(activity.getYear()), BODY_FONT, false);
        }

        insertEmptyRow(table);
    }
}
