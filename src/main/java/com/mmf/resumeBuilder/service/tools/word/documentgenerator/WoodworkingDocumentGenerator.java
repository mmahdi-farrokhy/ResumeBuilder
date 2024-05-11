package com.mmf.resumeBuilder.service.tools.word.documentgenerator;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import com.mmf.resumeBuilder.service.tools.word.FontProperties;
import com.mmf.resumeBuilder.service.tools.word.Symbol;
import com.mmf.resumeBuilder.service.tools.word.WordProcessing;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.mmf.resumeBuilder.service.tools.date.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.tools.date.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.tools.word.WordProcessing.*;
import static java.lang.String.valueOf;

public class WoodworkingDocumentGenerator implements DocumentGenerator {
    public static final String TWO_INCH_WIDTH = "3300";
    public static final String FIVE_INCH_WIDTH = "7700";
    private static final FontProperties NAME_ABBREVIATION_FONT = new FontProperties(24, "D14140", "Georgia (Headings)");
    private static final FontProperties NAME_FONT = new FontProperties(24, "000000", "Georgia (Headings)");
    private static final FontProperties HEADING_FONT = new FontProperties(10, "D14140", "Georgia (Headings)");
    private static final FontProperties BODY_FONT = new FontProperties(10, "000000", "Gill Sans MT (Headings)");
    private static final FontProperties CONTACT_INFO_FONT = new FontProperties(10, "FFFFFF", "Times New Roman");
    private static final FontProperties TITLE_FONT = new FontProperties(10, "000000", "Arial (Body)");
    private static final FontProperties DATE_FONT = new FontProperties(10, "d96a6a", "Gill Sans MT (Body)");
    private static final String SYMBOL_COLOR = "D14140";
    private static final Symbol NEW_LINE = new Symbol(SYMBOL_COLOR, '\n');
    private static final Symbol BULLET = new Symbol(SYMBOL_COLOR, '•');
    private static final Symbol DASH = new Symbol(SYMBOL_COLOR, '-');
    private static final Symbol COLON = new Symbol(SYMBOL_COLOR, ':');
    private static final Symbol PIPE = new Symbol(SYMBOL_COLOR, '|');
    private static final String TEMPLATE_FILE = "word templates/english/Woodworking.docx";

    @Override
    public void generateWordDocument(Resume resume) {
        try {
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

            XWPFDocument document = WordProcessing.openWordTemplate(TEMPLATE_FILE, 0.5, 0.5, 0.66, 0.25);

            XWPFTable table = WordProcessing.createTable(document);

            if (personalInformation != null) {
                createNameTitle(table, personalInformation);

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

                if (contactInformation != null && !contactInformation.isEmpty()) {
                    addContactInformationToDocument(table, contactInformation);
                }

                FileOutputStream out = new FileOutputStream(WordProcessing.generateFilePath(personalInformation, ResumeTheme.Woodworking));
                document.write(out);
                out.close();
                document.close();
            } else {
                throw new IOException("Contact information can not be empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNameTitle(XWPFTable table, PersonalInformation personalInformation) {
        String fullName = personalInformation.generateFullName();
        XWPFTableRow row = WordProcessing.createRow(table);
        String abbreviation = generateFullNameAbbreviation(fullName);

        addRunToParagraph(WordProcessing.createCell(row, TWO_INCH_WIDTH).addParagraph(), abbreviation, NAME_ABBREVIATION_FONT, true);
//        addRunToParagraph(createCell(row, HALF_INCH_WIDTH).addParagraph(), "", NAME_ABBREVIATION_FONT, true);
        addRunToParagraph(WordProcessing.createCell(row, FIVE_INCH_WIDTH).addParagraph(), fullName, NAME_FONT, true);
    }

    private String generateFullNameAbbreviation(String fullName) {
        StringBuilder abbreviation = new StringBuilder();
        for (String name : fullName.split("\\s+")) {
            abbreviation.append(Character.toUpperCase(name.charAt(0)));
        }

        return abbreviation.toString();
    }

    private void addSummary(XWPFTable table, Summary summary) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Summary", HEADING_FONT, true);

        List<String> list = Arrays.stream(summary.getText().split("\\n")).toList();
        XWPFParagraph bodyParagraph = rowCell.addParagraph();
        addRunToParagraph(bodyParagraph, list, BODY_FONT, NEW_LINE, false);
        rowCell.addParagraph();
    }

    private void addJobExperiencesToDocument(XWPFTable table, List<JobExperience> jobExperiences) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Experiences", HEADING_FONT, true);

        int jobNumber = 0;
        for (JobExperience job : jobExperiences) {
            jobNumber++;
            String jobDuration = calculateDuration(job.getStartDate(), job.getEndDate());
            addRunToParagraph(rowCell.addParagraph(), jobDuration, DATE_FONT, false);

            XWPFParagraph titleParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(job.getTitle(),
                    job.getCompanyName(),
                    job.getLocation().getCity());
            List<String> split = Arrays.stream(job.getDescription().split("\\n")).toList();

            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, true);
            XWPFParagraph paragraph = rowCell.addParagraph();
            addRunToParagraph(paragraph, split, BODY_FONT, NEW_LINE, false);

            if (jobNumber < jobExperiences.size())
                insertNewLine(paragraph);
        }

        rowCell.addParagraph();
    }

    private void addFormerColleaguesToDocument(XWPFTable table, List<FormerColleague> formerColleagues) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Former Colleagues", HEADING_FONT, true);

        for (FormerColleague formerColleague : formerColleagues) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(formerColleague.getFullName(),
                    formerColleague.getPosition(),
                    formerColleague.getPhoneNumber());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, BULLET, false);
        }

        rowCell.addParagraph();
    }

    private void addSkillsToDocument(XWPFTable table, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Skills", HEADING_FONT, true);

        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            skills = hardSkills.stream().map(h -> h.getType().toString()).toList();
        }

        if (softSkills != null) {
            List<String> softSkillList = softSkills.stream().map(SoftSkill::getTitle).toList();
            skills = Stream.concat(skills.stream(), softSkillList.stream()).toList();
        }

        XWPFParagraph titleParagraph = rowCell.addParagraph();
        addRunToParagraph(titleParagraph, skills, BODY_FONT, BULLET, false);
        rowCell.addParagraph();
    }

    private void addCoursesToDocument(XWPFTable table, List<Course> courses) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Courses", HEADING_FONT, true);

        for (Course course : courses) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(course.getName(), course.getInstitute());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        rowCell.addParagraph();
    }

    private void addProjectsToDocument(XWPFTable table, List<Project> projects) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Projects", HEADING_FONT, true);

        for (Project project : projects) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            String projectDuration = calculateDuration(project.getStartDate(), project.getEndDate());

            Map<String, String> titleParts = new LinkedHashMap<>() {{
                put(project.getName(), project.getReferenceLink());
                put(projectDuration, project.getReferenceLink());
                put(project.getStatus().toString(), project.getReferenceLink());
            }};

            addHyperlinkRunToParagraph(titleParagraph, titleParts, TITLE_FONT, PIPE, true, ParagraphAlignment.LEFT);

            if (project.getDescription() != null) {
                String[] descriptionText = project.getDescription().split("\\n");
                for (String descriptionBlock : descriptionText) {
                    XWPFParagraph descriptionParagraph = rowCell.addParagraph();
                    addRunToParagraph(descriptionParagraph, descriptionBlock, BODY_FONT, false);
                }
            }
        }

        rowCell.addParagraph();
    }

    private void addEducationsToDocument(XWPFTable table, List<Education> educations) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Education", HEADING_FONT, true);

        for (Education education : educations) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            String major = education.getDegreeLevel() + " of " + education.getMajor();
            String educationDuration = calculateYearDuration(education.getStartYear(), education.getEndYear());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            List<String> titleParts = Arrays.asList(major,
                    education.getUniversity(),
                    educationDuration);
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        rowCell.addParagraph();
    }

    private void addTeachingAssistanceToDocument(XWPFTable table, List<TeachingAssistance> teachingAssistance) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Teaching Assistance", HEADING_FONT, true);

        for (TeachingAssistance ta : teachingAssistance) {
            XWPFParagraph paragraph = rowCell.addParagraph();
            String duration = calculateDuration(ta.getStartDate(), ta.getEndDate());
            List<String> titleParts = Arrays.asList(ta.getTitle(),
                    ta.getUniversity(),
                    duration);

            addSymbolToParagraph(paragraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(paragraph, titleParts, BODY_FONT, PIPE, false);
        }

        rowCell.addParagraph();
    }

    private void addPresentationsToDocument(XWPFTable table, List<Presentation> presentations) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Presentations", HEADING_FONT, true);

        for (Presentation presentation : presentations) {
            XWPFParagraph presentationParagraph = rowCell.addParagraph();

            List<String> titlePArt = Arrays.asList(presentation.getTitle(), presentation.getDate().toString());

            addSymbolToParagraph(presentationParagraph, DASH, TITLE_FONT.getSize());
            addRunToParagraph(presentationParagraph, titlePArt, BODY_FONT, PIPE, true);

            String[] descriptionText = presentation.getDescription().split("\\n");
            int paragraphNumber = 0;
            for (String descriptionParagraph : descriptionText) {
                paragraphNumber++;
                XWPFParagraph paragraph = rowCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);

                if (paragraphNumber < descriptionText.length)
                    insertNewLine(paragraph);
            }
        }

        rowCell.addParagraph();
    }

    private void addPatentsToDocument(XWPFTable table, List<Patent> patents) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Patents", HEADING_FONT, true);

        for (Patent patent : patents) {
            XWPFParagraph patentParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(patent.getTitle(), patent.getRegistrationNumber(), patent.getRegistrationDate().toString());

            addSymbolToParagraph(patentParagraph, DASH, TITLE_FONT.getSize());
            addRunToParagraph(patentParagraph, titleParts, BODY_FONT, PIPE, true);

            String[] descriptionText = patent.getDescription().split("\\n");
            int paragraphNumber = 0;
            for (String descriptionParagraph : descriptionText) {
                paragraphNumber++;
                XWPFParagraph paragraph = rowCell.addParagraph();
                addRunToParagraph(paragraph, descriptionParagraph, BODY_FONT, false);

                if (paragraphNumber < descriptionText.length)
                    insertNewLine(paragraph);
            }
        }

        rowCell.addParagraph();
    }

    private void addResearchesToDocument(XWPFTable table, List<Research> researches) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Researches", HEADING_FONT, true);

        int researchNumber = 0;
        for (Research research : researches) {
            researchNumber++;
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            XWPFParagraph bodyParagraph = rowCell.addParagraph();

            Map<String, String> titleParts = new LinkedHashMap<>() {{

                addSymbolToParagraph(titleParagraph, DASH, TITLE_FONT.getSize());
                put(research.getTitle(), research.getReferenceLink());
                put(research.getPublisher(), research.getReferenceLink());
                put(research.getDate().toString(), research.getReferenceLink());
            }};
            List<String> descriptionText = Arrays.stream(research.getDescription().split("\\n")).toList();

            addHyperlinkRunToParagraph(titleParagraph, titleParts, TITLE_FONT, PIPE, true, ParagraphAlignment.LEFT);
            addRunToParagraph(bodyParagraph, descriptionText, BODY_FONT, NEW_LINE, false);

            if (researchNumber < researches.size())
                insertNewLine(bodyParagraph);
        }

        rowCell.addParagraph();
    }

    private void addLanguagesToDocument(XWPFTable table, List<Language> languages) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Languages", HEADING_FONT, true);

        XWPFParagraph titleParagraph = rowCell.addParagraph();
        int languageNumber = 0;
        for (Language language : languages) {
            languageNumber++;
            addRunToParagraph(titleParagraph, language.getName().toString(), BODY_FONT, false);
            addSymbolToParagraph(titleParagraph, COLON, BODY_FONT.getSize());

            addRunToParagraph(titleParagraph, language.estimateAverageLevel().toString(), BODY_FONT, false);

            if (languageNumber < languages.size())
                addSymbolToParagraph(titleParagraph, BULLET, BODY_FONT.getSize());
        }

        rowCell.addParagraph();
    }

    private void addHobbiesToDocument(XWPFTable table, List<Hobby> hobbies) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Hobbies", HEADING_FONT, true);

        XWPFParagraph titleParagraph = rowCell.addParagraph();

        List<String> hobbyList = new LinkedList<>();
        for (Hobby hobby : hobbies) {
            hobbyList.add(hobby.getTitle());
        }

        addRunToParagraph(titleParagraph, hobbyList, BODY_FONT, BULLET, false);
        rowCell.addParagraph();
    }

    private void addMembershipsToDocument(XWPFTable table, List<Membership> memberships) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Memberships", HEADING_FONT, true);

        for (Membership membership : memberships) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(membership.getTitle(), membership.getDate().toString());

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        rowCell.addParagraph();
    }

    private void addVolunteerActivitiesToDocument(XWPFTable table, List<VolunteerActivity> volunteerActivities) {
        XWPFTableRow row = table.createRow();
        XWPFTableCell rowCell = WordProcessing.getRowCell(row, 1);
        addRunToParagraph(rowCell.addParagraph(), "Volunteer Activities", HEADING_FONT, true);

        for (VolunteerActivity activity : volunteerActivities) {
            XWPFParagraph titleParagraph = rowCell.addParagraph();
            List<String> titleParts = Arrays.asList(activity.getTitle(), valueOf(activity.getYear()));

            addSymbolToParagraph(titleParagraph, DASH, BODY_FONT.getSize());
            addRunToParagraph(titleParagraph, titleParts, BODY_FONT, PIPE, false);
        }

        rowCell.addParagraph();
    }

    private void addContactInformationToDocument(XWPFTable table, List<ContactMethod> contactInformation) {
        Optional<ContactMethod> phone = getContactInfoByType(contactInformation, ContactType.Phone_Number);
        Optional<ContactMethod> email = getContactInfoByType(contactInformation, ContactType.Email);
        Optional<ContactMethod> address = getContactInfoByType(contactInformation, ContactType.Address);
        Optional<ContactMethod> linkedIn = getContactInfoByType(contactInformation, ContactType.LinkedIn);
        Optional<ContactMethod> gitHub = getContactInfoByType(contactInformation, ContactType.GitHub);
        Optional<ContactMethod> others = contactInformation.stream().filter(
                c -> !c.getType().equals(ContactType.Phone_Number)
                        && !c.getType().equals(ContactType.Email)
                        && !c.getType().equals(ContactType.Address)
                        && !c.getType().equals(ContactType.LinkedIn)
                        && !c.getType().equals(ContactType.GitHub)).findFirst();

        List<XWPFTableRow> rows = table.getRows();
        XWPFTableRow row = rows.get(2);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        String separator = "________________________";

        if (phone.isPresent()) {
            String phoneLink = "tel:" + phone.get().getContent().replaceFirst("0", "+98");
            insertNewLine(paragraph);
            addHyperlinkRunToParagraph(paragraph, phoneLink, phone.get().getContent(), CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        if (email.isPresent()) {
            String emailLink = "mailto:" + email.get().getContent();
            addHyperlinkRunToParagraph(paragraph, emailLink, email.get().getContent(), CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        if (address.isPresent()) {
            addRunToParagraph(paragraph, address.get().getContent(), CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        if (linkedIn.isPresent()) {
            addHyperlinkRunToParagraph(paragraph, linkedIn.get().getContent(), "LinkedIn", CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        if (gitHub.isPresent()) {
            addHyperlinkRunToParagraph(paragraph, gitHub.get().getContent(), "GitHub", CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        if (others.isPresent()) {
            addRunToParagraph(paragraph, others.get().getContent(), CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            addRunToParagraph(paragraph, separator, CONTACT_INFO_FONT, false);
            insertNewLine(paragraph);
            insertNewLine(paragraph);
        }

        cell.addParagraph();
    }

    private static Optional<ContactMethod> getContactInfoByType(List<ContactMethod> contactInformation, ContactType type) {
        return contactInformation
                .stream()
                .filter(c -> c.getType().equals(type))
                .findFirst();
    }
}
