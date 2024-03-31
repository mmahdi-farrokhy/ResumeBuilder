package com.mmf.resumeBuilder.service.wordtools;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ClassicAccountingDocumentGenerator implements DocumentGenerator {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    private static final String DATE_COLOR = "575355";
    private static final String TITLE_COLOR = "2E471D";
    public static final String BODY_COLOR = "212010";
    public static final String BOLD_COLOR = "000000";
    private static final String DASH_COLOR = "558335";
    private static final int TITLE_SIZE = 16;
    private static final String TITLE_FONT_FAMILY = "Speak Pro (Headings)";
    public static final int BODY_SIZE = 10;
    public static final int INDENTATION = 300;
    private static final FontProperties headingFont = new FontProperties(TITLE_SIZE, TITLE_COLOR, "Gill Sans MT (Headings)");


    @Override
    public void generateWordDocument(Resume resume) {
        try {
            XWPFDocument document = createDocument(0.5, 0.5, 0.5, 0.5);

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
                createNameTitle(document, personalInformation, contactInformation);

                if (summary != null) {
                    addSummary(document, summary);
                }

                if (jobExperiences != null && !jobExperiences.isEmpty()) {
                    addJobExperiencesToDocument(document, jobExperiences);
                }

                if (formerColleagues != null && !formerColleagues.isEmpty()) {
                    addFormerColleaguesToDocument(document, formerColleagues);
                }

                if ((hardSkills != null && !hardSkills.isEmpty()) || (softSkills != null && !softSkills.isEmpty())) {
                    addSkillsToDocument(document, hardSkills, softSkills);
                }

                if (courses != null && !courses.isEmpty()) {
                    addCoursesToDocument(document, courses);
                }

                if (projects != null && !projects.isEmpty()) {
                    addProjectsToDocument(document, projects);
                }

                if (educations != null && !educations.isEmpty()) {
                    addEducationsToDocument(document, educations);
                }

                if (teachingAssistance != null && !teachingAssistance.isEmpty()) {
                    addTeachingAssistanceToDocument(document, teachingAssistance);
                }

                if (presentations != null && !presentations.isEmpty()) {
                    addPresentationsToDocument(document, presentations);
                }

                if (patents != null && !patents.isEmpty()) {
                    addPatentsToDocument(document, patents);
                }

                if (researches != null && !researches.isEmpty()) {
                    addResearchesToDocument(document, researches);
                }

                if (languages != null && !languages.isEmpty()) {
                    addLanguagesToDocument(document, languages);
                }

                if (hobbies != null && !hobbies.isEmpty()) {
                    addHobbiesToDocument(document, hobbies);
                }

                if (memberships != null && !memberships.isEmpty()) {
                    addMembershipsToDocument(document, memberships);
                }

                if (volunteerActivities != null && !volunteerActivities.isEmpty()) {
                    addVolunteerActivitiesToDocument(document, volunteerActivities);
                }

                FileOutputStream out = new FileOutputStream(STORE_PATH + "Test.docx");
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

    private static void createNameTitle(XWPFDocument document, PersonalInformation personalInformation, List<ContactMethod> contactInformation) {
        String address = "";
        String email = "";
        String phoneNumber = "";

        if (contactInformation != null && !contactInformation.isEmpty()) {
            Optional<ContactMethod> addressInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Address).findFirst();
            if (addressInfo.isPresent())
                address = addressInfo.get().getContent();

            Optional<ContactMethod> emailInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Email).findFirst();
            if (emailInfo.isPresent())
                email = emailInfo.get().getContent();

            Optional<ContactMethod> phoneNumberInfo = contactInformation.stream().filter(c -> c.getType() == ContactType.Phone_Number).findFirst();
            if (phoneNumberInfo.isPresent())
                phoneNumber = phoneNumberInfo.get().getContent();
        }

        addRowToTable(document, asList(personalInformation.getFirstName(), address), new FontProperties(20, TITLE_COLOR, "Gill Sans MT (Headings)"), false, 0);
        addRowToTable(document, asList(personalInformation.getLastName(), phoneNumber + " | " + email), new FontProperties(15, BOLD_COLOR, "Gill Sans MT (Body)"), false, 0);
        document.createParagraph();
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        FontProperties font = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
        addRowToTable(document, singletonList(summary.getText()), font, false, 0);
        document.createParagraph();
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
        addRowToTable(document, singletonList("Experiences"), headingFont, false, 0);

        for (JobExperience jobExperience : jobExperiences) {

            FontProperties dateFont = new FontProperties(BODY_SIZE, DATE_COLOR, "Gill Sans MT");
            addRowToTable(document, singletonList(calculateDuration(jobExperience.getStartDate(), jobExperience.getEndDate())), dateFont, false, INDENTATION);

            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
            String jobTitle = jobExperience.getTitle() + " | " +
                    jobExperience.getCompanyName() + " | " +
                    jobExperience.getLocation().getCityName();
            addRowToTable(document, singletonList(jobTitle), titleFont, true, INDENTATION);

            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList(jobExperience.getDescription()), bodyFont, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        addRowToTable(document, singletonList("Former Colleagues"), headingFont, false, 0);

        for (FormerColleague formerColleague : formerColleagues) {
            String formerColleagueInfo = formerColleague.getFullName() + " • " + formerColleague.getPosition() + " • " + formerColleague.getPhoneNumber();
            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
//            addDashedRowToTable(document, List.of(formerColleagueInfo), bodyFont, TITLE_COLOR, false, INDENTATION);
            addDashedRowToTable(document, List.of(formerColleagueInfo), bodyFont, DASH_COLOR, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        addRowToTable(document, singletonList("Skills"), headingFont, false, 0);
        StringBuilder formerColleagueInfo = new StringBuilder();

        if (hardSkills != null) {
            for (HardSkill hardSkill : hardSkills) {
                formerColleagueInfo.append(hardSkill.getType()).append(" • ");
            }
        }

        if (softSkills != null) {
            for (SoftSkill softSkill : softSkills) {
                formerColleagueInfo.append(softSkill.getTitle()).append(" • ");
            }
        }

        FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
        addRowToTable(document, List.of(formerColleagueInfo.toString()), bodyFont, false, INDENTATION);
        document.createParagraph();
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        addRowToTable(document, singletonList("Courses"), headingFont, false, 0);

        for (Course course : courses) {
            String courseInfo = course.getName() + " | " + course.getInstitute();
            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
//            addDashedRowToTable(document, List.of(courseInfo), bodyFont, TITLE_COLOR, false, INDENTATION);
            addDashedRowToTable(document, List.of(courseInfo), bodyFont, DASH_COLOR, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
        addRowToTable(document, singletonList("Projects"), headingFont, false, 0);

        for (Project project : projects) {
            String projectTitle = project.getName() + " | " +
                    calculateDuration(project.getStartDate(), project.getEndDate()) + " | " +
                    project.getStatus() +
                    " (Click to open the project)";
            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
            addHyperlinkRowToTable(document, project.getReferenceLink(), projectTitle, titleFont, true, INDENTATION);

            FontProperties descriptionFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList(project.getDescription()), descriptionFont, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        addRowToTable(document, singletonList("Education"), headingFont, false, 0);

        for (Education education : educations) {
            String educationTitle = education.getMajor() + " | " +
                    education.getUniversity() + " | " +
                    education.getDegreeLevel() + " | " +
                    calculateYearDuration(education.getStartYear(), education.getEndYear());
            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
//            addDashedRowToTable(document, singletonList(educationTitle), titleFont, TITLE_COLOR, false, INDENTATION);
            addDashedRowToTable(document, singletonList(educationTitle), titleFont, DASH_COLOR, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistanceList) {
        addRowToTable(document, singletonList("Teaching Assistance"), headingFont, false, 0);

        for (TeachingAssistance teachingAssistance : teachingAssistanceList) {
            String teachingAssistanceTitle = teachingAssistance.getTitle() + " | " +
                    teachingAssistance.getUniversity() + " | " +
                    calculateDuration(teachingAssistance.getStartDate(), teachingAssistance.getEndDate());

            FontProperties titleFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addDashedRowToTable(document, singletonList(teachingAssistanceTitle), titleFont, DASH_COLOR, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
        addRowToTable(document, singletonList("Presentations"), headingFont, false, 0);

        for (Presentation presentation : presentations) {
            String presentationTitle = presentation.getTitle() + " | " + presentation.getDate();
            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
            addDashedRowToTable(document, singletonList(presentationTitle), titleFont, DASH_COLOR, true, INDENTATION);

            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList("   " + presentation.getDescription()), bodyFont, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
        addRowToTable(document, singletonList("Patents"), headingFont, false, 0);

        for (Patent patent : patents) {
            String patentTitle = patent.getTitle() + " | " + patent.getRegistrationNumber() + " | " + patent.getRegistrationDate();
            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
            addDashedRowToTable(document, singletonList(patentTitle), titleFont, DASH_COLOR, true, INDENTATION);

            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList("   " + patent.getDescription()), bodyFont, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
        addRowToTable(document, singletonList("Researches"), headingFont, false, 0);

        for (Research research : researches) {
            String researchTitle = research.getTitle() + " | " +
                    research.getPublisher() + " | " +
                    research.getDate() +
                    " (Click to open the research)";
            FontProperties titleFont = new FontProperties(BODY_SIZE, BOLD_COLOR, "Gill Sans MT (Headings)");
            addDashedHyperlinkRowToTable(document, research.getReferenceLink(), researchTitle, titleFont, DASH_COLOR, true, INDENTATION);

            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList("   " + research.getDescription()), bodyFont, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
        addRowToTable(document, singletonList("Languages"), headingFont, false, 0);
        StringBuilder languageTitle = new StringBuilder();

        for (Language language : languages) {
            languageTitle.append(language.getName()).append(": ").append(language.estimateAverageLevel()).append(" • ");
        }

        FontProperties titleFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
        addRowToTable(document, singletonList(languageTitle.toString()), titleFont, false, INDENTATION);
        document.createParagraph();
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        addRowToTable(document, singletonList("Hobbies"), headingFont, false, 0);

        for (Hobby hobby : hobbies) {
            FontProperties titleFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList(hobby.getTitle() + " • "), titleFont, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        addRowToTable(document, singletonList("Memberships"), headingFont, false, 0);

        for (Membership membership : memberships) {
            String membershipInfo = " - " + membership.getTitle() + " | " + membership.getDate().getYear();
            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList(membershipInfo), bodyFont, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        addRowToTable(document, singletonList("Volunteer Activities"), headingFont, false, 0);

        for (VolunteerActivity volunteerActivity : volunteerActivities) {
            String volunteerActivityInfo = " - " + volunteerActivity.getTitle() + " | " + volunteerActivity.getYear();
            FontProperties bodyFont = new FontProperties(BODY_SIZE, BODY_COLOR, "Gill Sans MT (Body)");
            addRowToTable(document, singletonList(volunteerActivityInfo), bodyFont, false, INDENTATION);
        }

        document.createParagraph();
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
