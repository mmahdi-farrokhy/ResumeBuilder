package com.mmf.resumeBuilder.service.wordtools;

import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.entity.resume.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateDuration;
import static com.mmf.resumeBuilder.service.datetools.DateCalculation.calculateYearDuration;
import static com.mmf.resumeBuilder.service.wordtools.WordProcessing.*;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ClassicAccountingDocumentGenerator implements DocumentGenerator {
    public static final String STORE_PATH = System.getProperty("user.dir") + "\\src\\main\\resumes\\";
    private static final String DATE_COLOR = "575355";
    private static final String HEADING_COLOR = "2E471D";
    public static final String BODY_COLOR = "212010";
    public static final String BOLD_COLOR = "000000";
    private static final String DASH_COLOR = "558335";
    public static final int INDENTATION = 300;
    private static final FontProperties HEADING_FONT = new FontProperties(16, HEADING_COLOR, "Gill Sans MT (Headings)");
    private static final FontProperties BODY_FONT = new FontProperties(10, BODY_COLOR, "Gill Sans MT (Body)");
    private static final FontProperties TITLE_FONT = new FontProperties(10, BOLD_COLOR, "Gill Sans MT (Headings)");
    public static final FontProperties DATE_FONT = new FontProperties(10, DATE_COLOR, "Gill Sans MT");

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

        FontProperties font = new FontProperties(20, HEADING_COLOR, "Gill Sans MT (Headings)");
        FontProperties font1 = new FontProperties(15, BOLD_COLOR, "Gill Sans MT (Body)");

        addRowToTable(document, asList(personalInformation.getFirstName(), "", address), font, false, 0);
        addRowToTable(document, asList(personalInformation.getLastName(), phoneNumber, email), font1, false, 0);
        document.createParagraph();
    }

    private static void addSummary(XWPFDocument document, Summary summary) {
        addRowToTable(document, singletonList(summary.getText()), BODY_FONT, false, 0);
        document.createParagraph();
    }

    private static void addJobExperiencesToDocument(XWPFDocument document, List<JobExperience> jobExperiences) {
        addRowToTable(document, singletonList("Experiences"), HEADING_FONT, false, 0);

        for (JobExperience job : jobExperiences) {
            String jobDuration = calculateDuration(job.getStartDate(), job.getEndDate());
            String[] title = {job.getTitle(), job.getCompanyName(), job.getLocation().getCityName().toString()};

            addRowToTable(document, singletonList(jobDuration), DATE_FONT, false, INDENTATION);
            addRowToTableDelimitedBySymbol(document, asList(title), TITLE_FONT, true, INDENTATION, " | ", DASH_COLOR);
            addRowToTable(document, singletonList(job.getDescription()), BODY_FONT, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addFormerColleaguesToDocument(XWPFDocument document, List<FormerColleague> formerColleagues) {
        addRowToTable(document, singletonList("Former Colleagues"), HEADING_FONT, false, 0);

        for (FormerColleague formerColleague : formerColleagues) {
            String[] formerColleagueInfo = {formerColleague.getFullName(), formerColleague.getPosition(), formerColleague.getPhoneNumber()};
            addDashedRowToTableDelimitedBySymbol(document, asList(formerColleagueInfo), BODY_FONT, false, INDENTATION, " • ", DASH_COLOR);
        }

        document.createParagraph();
    }


    private static void addSkillsToDocument(XWPFDocument document, List<HardSkill> hardSkills, List<SoftSkill> softSkills) {
        addRowToTable(document, singletonList("Skills"), HEADING_FONT, false, 0);
        List<String> skills = new LinkedList<>();

        if (hardSkills != null) {
            for (HardSkill hardSkill : hardSkills) {
                skills.add(hardSkill.getType().toString());
            }
        }

        if (softSkills != null) {
            for (SoftSkill softSkill : softSkills) {
                skills.add(softSkill.getTitle());
            }
        }

        addRowToTableDelimitedBySymbol(document, skills, BODY_FONT, false, INDENTATION, " • ", DASH_COLOR);
        document.createParagraph();
    }

    private static void addCoursesToDocument(XWPFDocument document, List<Course> courses) {
        addRowToTable(document, singletonList("Courses"), HEADING_FONT, false, 0);

        for (Course course : courses) {
            String[] courseInfo = {course.getName(), course.getInstitute()};
            addDashedRowToTableDelimitedBySymbol(document, asList(courseInfo), BODY_FONT, false, INDENTATION, " | ", DASH_COLOR);
        }

        document.createParagraph();
    }

    private static void addProjectsToDocument(XWPFDocument document, List<Project> projects) {
        addRowToTable(document, singletonList("Projects"), HEADING_FONT, false, 0);

        for (Project project : projects) {
            String[] title = {project.getName(), calculateDuration(project.getStartDate(), project.getEndDate()), project.getStatus().toString() + " (Click to open the project)"};
            addHyperlinkRowToTableDelimitedBySymbol(document, project.getReferenceLink(), asList(title), TITLE_FONT, true, INDENTATION, " | ", DASH_COLOR);
            addRowToTable(document, singletonList(project.getDescription()), BODY_FONT, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addEducationsToDocument(XWPFDocument document, List<Education> educations) {
        addRowToTable(document, singletonList("Education"), HEADING_FONT, false, 0);

        for (Education education : educations) {
            String[] title = {education.getUniversity(),
                    education.getDegreeLevel().toString(),
                    calculateYearDuration(education.getStartYear(), education.getEndYear())};

            addDashedRowToTableDelimitedBySymbol(document, asList(title), TITLE_FONT, false, INDENTATION, " | ", DASH_COLOR);
        }

        document.createParagraph();
    }

    private static void addTeachingAssistanceToDocument(XWPFDocument document, List<TeachingAssistance> teachingAssistanceList) {
        addRowToTable(document, singletonList("Teaching Assistance"), HEADING_FONT, false, 0);

        for (TeachingAssistance ta : teachingAssistanceList) {
            String[] title = {ta.getTitle(),
                    ta.getUniversity(),
                    calculateDuration(ta.getStartDate(), ta.getEndDate())};

            addDashedRowToTableDelimitedBySymbol(document, asList(title), BODY_FONT, false, INDENTATION, " | ", DASH_COLOR);
        }

        document.createParagraph();
    }

    private static void addPresentationsToDocument(XWPFDocument document, List<Presentation> presentations) {
        addRowToTable(document, singletonList("Presentations"), HEADING_FONT, false, 0);

        for (Presentation presentation : presentations) {
            String[] title = {presentation.getTitle(), presentation.getDate().toString()};
            addDashedRowToTableDelimitedBySymbol(document, asList(title), TITLE_FONT, true, INDENTATION, " | ", DASH_COLOR);
            addRowToTable(document, singletonList("   " + presentation.getDescription()), BODY_FONT, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addPatentsToDocument(XWPFDocument document, List<Patent> patents) {
        addRowToTable(document, singletonList("Patents"), HEADING_FONT, false, 0);

        for (Patent patent : patents) {
            String[] title = {patent.getTitle(),
                    patent.getRegistrationNumber(),
                    patent.getRegistrationDate().toString()};

            addDashedRowToTableDelimitedBySymbol(document, asList(title), TITLE_FONT, true, INDENTATION, " | ", DASH_COLOR);
            addRowToTable(document, singletonList("   " + patent.getDescription()), BODY_FONT, false, INDENTATION);
        }

        document.createParagraph();
    }

    private static void addResearchesToDocument(XWPFDocument document, List<Research> researches) {
        addRowToTable(document, singletonList("Researches"), HEADING_FONT, false, 0);

        for (Research research : researches) {
            String[] title = {research.getTitle(),
                    research.getPublisher(),
                    research.getDate() + " (Click to open the research)"};

            addDashedHyperlinkRowToTableDelimitedBySymbol(document, research.getReferenceLink(), asList(title), TITLE_FONT, true, INDENTATION, " | ", DASH_COLOR);
            addRowToTable(document, singletonList("   " + research.getDescription()), BODY_FONT, false, INDENTATION);
            document.createParagraph();
        }
    }

    private static void addLanguagesToDocument(XWPFDocument document, List<Language> languages) {
        addRowToTable(document, singletonList("Languages"), HEADING_FONT, false, 0);
        List<String> languageList = new LinkedList<>();

        for (Language language : languages) {
            String title = language.getName() + ": " + language.estimateAverageLevel();
            languageList.add(title);
        }

        addRowToTableDelimitedBySymbol(document, languageList, BODY_FONT, false, INDENTATION, " • ", DASH_COLOR);
        document.createParagraph();
    }

    private static void addHobbiesToDocument(XWPFDocument document, List<Hobby> hobbies) {
        addRowToTable(document, singletonList("Hobbies"), HEADING_FONT, false, 0);
        List<String> hobbyList = new LinkedList<>();

        for (Hobby hobby : hobbies) {
            hobbyList.add(hobby.getTitle());
        }

        addRowToTableDelimitedBySymbol(document, hobbyList, BODY_FONT, false, INDENTATION, " • ", DASH_COLOR);
        document.createParagraph();
    }

    private static void addMembershipsToDocument(XWPFDocument document, List<Membership> memberships) {
        addRowToTable(document, singletonList("Memberships"), HEADING_FONT, false, 0);

        for (Membership membership : memberships) {
            String[] membershipInfo = {membership.getTitle(), valueOf(membership.getDate().getYear())};
            addDashedRowToTableDelimitedBySymbol(document, asList(membershipInfo), BODY_FONT, false, INDENTATION, " | ", DASH_COLOR);
        }

        document.createParagraph();
    }

    private static void addVolunteerActivitiesToDocument(XWPFDocument document, List<VolunteerActivity> volunteerActivities) {
        addRowToTable(document, singletonList("Volunteer Activities"), HEADING_FONT, false, 0);

        for (VolunteerActivity activity : volunteerActivities) {
            String[] activityInfo = {activity.getTitle(), valueOf(activity.getYear())};
            addDashedRowToTableDelimitedBySymbol(document, asList(activityInfo), BODY_FONT, false, INDENTATION, " | ", DASH_COLOR);
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
