package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.data.dao.ResumeDAO;
import com.mmf.resumeBuilder.data.dao.UserDAO;
import com.mmf.resumeBuilder.enums.UserRole;
import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import com.mmf.resumeBuilder.enums.education.DegreeLevel;
import com.mmf.resumeBuilder.enums.education.Major;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import com.mmf.resumeBuilder.enums.job.JobCategory;
import com.mmf.resumeBuilder.enums.job.JobStatus;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.enums.location.City;
import com.mmf.resumeBuilder.enums.project.ProjectStatus;
import com.mmf.resumeBuilder.enums.user.detail.SeniorityLevel;
import com.mmf.resumeBuilder.model.User;
import com.mmf.resumeBuilder.model.resume.*;

import java.time.LocalDate;
import java.util.List;

public class DatabaseTest {
    public static void addResumeSections(ResumeDAO resumeDAO) {
        System.out.println("Fetching resume");
        int resumeId = 13;
        Resume resume = resumeDAO.findById(resumeId);

        List<ContactMethod> contactInformation = resumeDAO.fetchSection(resumeId, ContactMethod.class);
        resume.setContactInformation(contactInformation);

        ContactMethod newContactMethod = createContactMethod(resume, ContactType.Phone_Number, "09190763415");
        resumeDAO.addSection(newContactMethod);
        System.out.println("newContactMethod saved");

        Course newCourse = createCourse(resume, "Git/GitHub", "Google", null);
        resumeDAO.addSection(newCourse);
        System.out.println("newCourse saved");

        Education newEducation = createEducation(resume);
        resumeDAO.addSection(newEducation);
        System.out.println("newEducation saved");

        FormerColleague newFormerColleague = createFormerColleague1(resume);
        resumeDAO.addSection(newFormerColleague);
        System.out.println("newFormerColleague saved");

        HardSkill newHardSkill = createHardSkill(resume, HardSkillType.CSharp, HardSkillLevel.Advanced);
        resumeDAO.addSection(newHardSkill);
        System.out.println("newHardSkill saved");

        SoftSkill newSoftSkill = createSoftSkill(resume, "Negotiation");
        resumeDAO.addSection(newSoftSkill);
        System.out.println("newSoftSkill saved");

        Hobby newHobby = createHobby(resume, "Escape Room");
        resumeDAO.addSection(newHobby);
        System.out.println("newHobby saved");

        JobExperience newJobExperience = createJobExperience3(resume);
        resumeDAO.addSection(newJobExperience);
        System.out.println("newJobExperience saved");

        Language newLanguage = createLanguage(resume, LanguageName.Japanese, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Intermediate);
        resumeDAO.addSection(newLanguage);
        System.out.println("newLanguage saved");

        Membership newMembership = createMembership(resume, "Scientific Association of the Faculty of Computer Engineering", LocalDate.of(2019, 1, 1));
        resumeDAO.addSection(newMembership);
        System.out.println("newMembership saved");

        Patent newPatent = createPatent(resume);
        resumeDAO.addSection(newPatent);
        System.out.println("newPatent saved");

        Presentation newPresentation = createPresentation(resume);
        resumeDAO.addSection(newPresentation);
        System.out.println("newPresentation saved");

        Project newProject = createProject2(resume);
        resumeDAO.addSection(newProject);
        System.out.println("newProject saved");

        Publication newPublication = createPublication(resume);
        resumeDAO.addSection(newPublication);
        System.out.println("newPublication saved");

        Research newResearch = createResearch2(resume);
        resumeDAO.addSection(newResearch);
        System.out.println("newResearch saved");

        TeachingAssistance newTeachingAssistance = createTeachingAssistance2(resume);
        resumeDAO.addSection(newTeachingAssistance);
        System.out.println("newTeachingAssistance saved");

        VolunteerActivity newVolunteerActivity = createVolunteerActivity(resume, "Scientific Association of the Faculty of Computer Engineering", 2019);
        resumeDAO.addSection(newVolunteerActivity);
        System.out.println("newVolunteerActivity saved");
    }

    public static void findResumeAndItsSectionsInDatabase(ResumeDAO resumeDAO) {
        int resumeId = 13;
        Resume resume = resumeDAO.findById(resumeId);
        PersonalInformation user = resume.getPersonalInformation();
        List<ContactMethod> contactInformation = resumeDAO.fetchSection(resumeId, ContactMethod.class);
        List<Education> educations = resumeDAO.fetchSection(resumeId, Education.class);
        List<TeachingAssistance> teachingAssistance = resumeDAO.fetchSection(resumeId, TeachingAssistance.class);
        List<JobExperience> jobExperiences = resumeDAO.fetchSection(resumeId, JobExperience.class);
        List<FormerColleague> formerColleagues = resumeDAO.fetchSection(resumeId, FormerColleague.class);
        List<Research> researches = resumeDAO.fetchSection(resumeId, Research.class);
        List<Course> courses = resumeDAO.fetchSection(resumeId, Course.class);
        List<HardSkill> hardSkills = resumeDAO.fetchSection(resumeId, HardSkill.class);
        List<SoftSkill> softSkills = resumeDAO.fetchSection(resumeId, SoftSkill.class);
        List<Language> languages = resumeDAO.fetchSection(resumeId, Language.class);
        List<Project> projects = resumeDAO.fetchSection(resumeId, Project.class);
        List<Patent> patents = resumeDAO.fetchSection(resumeId, Patent.class);
        List<Presentation> presentations = resumeDAO.fetchSection(resumeId, Presentation.class);
        List<Award> awards = resumeDAO.fetchSection(resumeId, Award.class);
        List<Publication> publications = resumeDAO.fetchSection(resumeId, Publication.class);
        List<VolunteerActivity> volunteerActivities = resumeDAO.fetchSection(resumeId, VolunteerActivity.class);
        List<Membership> memberships = resumeDAO.fetchSection(resumeId, Membership.class);
        List<Hobby> hobbies = resumeDAO.fetchSection(resumeId, Hobby.class);

        String expectedResult =
                "user:User{id=13, firstName='Mohammad Mahdi\n" +
                        ", lastName='Farrokhy\n" +
                        ", phoneNumber='09017743009\n" +
                        ", maritalStatus=Single\n" +
                        ", gender=Male\n" +
                        ", militaryServiceStatus=Exempted\n" +
                        ", birthDate=1999-07-29\n" +
                        ", foreigner=false\n" +
                        ", disabilityType=None\n" +
                        "}\n" +
                        "_________________________________________\n" +
                        "[ContactMethod{id=39\n" +
                        ", type=Address\n" +
                        ", value='Tehran, Pardis County\n" +
                        "}, ContactMethod{id=40\n" +
                        ", type=Email\n" +
                        ", value='mmahdifarrokhy@gmail.com\n" +
                        "}, ContactMethod{id=41\n" +
                        ", type=LinkedIn\n" +
                        ", value='https://www.linkedin.com/in/mmahdi-farrokhy/\n" +
                        "}, ContactMethod{id=42\n" +
                        ", type=GitHub\n" +
                        ", value='https://github.com/mmahdi-farrokhy\n" +
                        "}, ContactMethod{id=43\n" +
                        ", type=Phone Number\n" +
                        ", value='09190763415\n" +
                        "}, ContactMethod{id=44\n" +
                        ", type=Phone Number\n" +
                        ", value='09190763415\n" +
                        "}, ContactMethod{id=45\n" +
                        ", type=Phone Number\n" +
                        ", value='09190763415\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "[Education{id=13\n" +
                        ", degreeLevel=Bachelor\n" +
                        ", major=Computer Engineering\n" +
                        ", university='Semnan University\n" +
                        ", gpa=16.0\n" +
                        ", startYear=2017\n" +
                        ", endYear=2021\n" +
                        "}, Education{id=14\n" +
                        ", degreeLevel=Bachelor\n" +
                        ", major=Computer Engineering\n" +
                        ", university='Semnan University\n" +
                        ", gpa=16.0\n" +
                        ", startYear=2017\n" +
                        ", endYear=2021\n" +
                        "}, Education{id=15\n" +
                        ", degreeLevel=Bachelor\n" +
                        ", major=Computer Engineering\n" +
                        ", university='Semnan University\n" +
                        ", gpa=16.0\n" +
                        ", startYear=2017\n" +
                        ", endYear=2021\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "teachingAssistance:[TeachingAssistance{id=8\n" +
                        ", title='Advanced Programming (Java)\n" +
                        ", university='Semnan University\n" +
                        ", startDate=2019-01-01\n" +
                        ", endDate=2019-06-30\n" +
                        "}, TeachingAssistance{id=9\n" +
                        ", title='Digital Electronic (MOSFET Transistors)\n" +
                        ", university='Semnan University\n" +
                        ", startDate=2019-01-01\n" +
                        ", endDate=2019-06-30\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "jobExperiences:[JobExperience{id=14\n" +
                        ", title='Software Developer And Consultant\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Junior\n" +
                        ", companyName='Negar Andishgan Co. Ltd.\n" +
                        ", description='Developing and refactoring NrSign.EMG medical test software\n" +
                        ", startDate=2023-01-07\n" +
                        ", endDate=null\n" +
                        ", status=Occupied\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}, JobExperience{id=15\n" +
                        ", title='C# Developer\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Junior\n" +
                        ", companyName='Zarvan Stun Khodro\n" +
                        ", description='Developing automotive software Diag and Remap\n" +
                        ", startDate=2022-04-17\n" +
                        ", endDate=2022-12-21\n" +
                        ", status=Finished\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}, JobExperience{id=16\n" +
                        ", title='Java/Spring Developer\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Mid Level\n" +
                        ", companyName='Blu Bank\n" +
                        ", description='Developing new sections in Blu environment\n" +
                        ", startDate=2024-04-02\n" +
                        ", endDate=null\n" +
                        ", status=Occupied\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}, JobExperience{id=17\n" +
                        ", title='Java/Spring Developer\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Mid Level\n" +
                        ", companyName='Blu Bank\n" +
                        ", description='Developing new sections in Blu environment\n" +
                        ", startDate=2024-04-02\n" +
                        ", endDate=null\n" +
                        ", status=Occupied\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "formerColleagues:[FormerColleague{id=23\n" +
                        ", fullName='Ehsan Arbabi\n" +
                        ", position='Manager\n" +
                        ", organizationName='Negar Andishgan Co. Ltd.\n" +
                        ", phoneNumber='09123456789\n" +
                        "}, FormerColleague{id=24\n" +
                        ", fullName='Mohammad Hossein Ommi\n" +
                        ", position='CEO\n" +
                        ", organizationName='Zarvan Stun Khodro\n" +
                        ", phoneNumber='09123456789\n" +
                        "}, FormerColleague{id=25\n" +
                        ", fullName='Ehsan Arbabi\n" +
                        ", position='Manager\n" +
                        ", organizationName='Negar Andishgan Co. Ltd.\n" +
                        ", phoneNumber='09123456789\n" +
                        "}, FormerColleague{id=26\n" +
                        ", fullName='Ehsan Arbabi\n" +
                        ", position='Manager\n" +
                        ", organizationName='Negar Andishgan Co. Ltd.\n" +
                        ", phoneNumber='09123456789\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "researches:[Research{id=7\n" +
                        ", title='Researching about Clean Code\n" +
                        ", publisher='Mohammad Mahdi Farrokhy\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/CleanCodeBook\n" +
                        ", date=2022-07-01\n" +
                        ", description='In this repository you can read the simplified summary of Clean Code book by Robert C. Martin in separated chapters.\n" +
                        "}, Research{id=8\n" +
                        ", title='Convolutional Neural Networks\n" +
                        ", publisher='Mohammad Mahdi Farrokhy\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/Convolutional_Neural_Network\n" +
                        ", date=2022-11-05\n" +
                        ", description='I researched about CNN which is a subject under Machine Learning. And also developed a 'hand written digit recognition system' using VHDL\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "courses:[Course{id=26\n" +
                        ", name='Java Expert\n" +
                        ", institute='7Learn\n" +
                        ", credentialId='https://7learn.com/crt?h=bdP9hiSTF4\n" +
                        "}, Course{id=27\n" +
                        ", name='Spring Boot - Chad Darby\n" +
                        ", institute='Udemy\n" +
                        ", credentialId='null\n" +
                        "}, Course{id=28\n" +
                        ", name='Git/GitHub\n" +
                        ", institute='Google\n" +
                        ", credentialId='null\n" +
                        "}, Course{id=29\n" +
                        ", name='Git/GitHub\n" +
                        ", institute='Google\n" +
                        ", credentialId='null\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "hardSkills:[HardSkill{id=23\n" +
                        ", type=Spring Boot\n" +
                        ", level=Beginner\n" +
                        "}, HardSkill{id=24\n" +
                        ", type=Java\n" +
                        ", level=Intermediate\n" +
                        "}, HardSkill{id=25\n" +
                        ", type=CSharp\n" +
                        ", level=Advanced\n" +
                        "}, HardSkill{id=26\n" +
                        ", type=CSharp\n" +
                        ", level=Advanced\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "softSkills:[SoftSkill{id=13\n" +
                        ", title='Teaching\n" +
                        "}, SoftSkill{id=14\n" +
                        ", title='Presentation\n" +
                        "}, SoftSkill{id=15\n" +
                        ", title='Negotiation\n" +
                        "}, SoftSkill{id=16\n" +
                        ", title='Negotiation\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "languages:[Language{id=19\n" +
                        ", name=German\n" +
                        ", speakingLevel=Pre Intermediate\n" +
                        ", writingLevel=Pre Intermediate\n" +
                        ", readingLevel=Pre Intermediate\n" +
                        ", listeningLevel=Pre Intermediate\n" +
                        ", researchingLevel=Pre Intermediate\n" +
                        "}, Language{id=20\n" +
                        ", name=English\n" +
                        ", speakingLevel=Intermediate\n" +
                        ", writingLevel=Intermediate\n" +
                        ", readingLevel=Upper Intermediate\n" +
                        ", listeningLevel=Upper Intermediate\n" +
                        ", researchingLevel=Upper Intermediate\n" +
                        "}, Language{id=21\n" +
                        ", name=Persian\n" +
                        ", speakingLevel=Native\n" +
                        ", writingLevel=Native\n" +
                        ", readingLevel=Native\n" +
                        ", listeningLevel=Native\n" +
                        ", researchingLevel=Native\n" +
                        "}, Language{id=22\n" +
                        ", name=Japanese\n" +
                        ", speakingLevel=Intermediate\n" +
                        ", writingLevel=Intermediate\n" +
                        ", readingLevel=Intermediate\n" +
                        ", listeningLevel=Intermediate\n" +
                        ", researchingLevel=Intermediate\n" +
                        "}, Language{id=23\n" +
                        ", name=Japanese\n" +
                        ", speakingLevel=Intermediate\n" +
                        ", writingLevel=Intermediate\n" +
                        ", readingLevel=Intermediate\n" +
                        ", listeningLevel=Intermediate\n" +
                        ", researchingLevel=Intermediate\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "projects:[Project{id=7\n" +
                        ", name='Resume Builder\n" +
                        ", description='I designed a resume builder web application using Spring Boot.\n" +
                        "This app has the capabilities of creating a new resume, editing an existing resume and removing a resume from profile.\n" +
                        "\n" +
                        "Stack:\n" +
                        "- Back-End: Java/Spring Boot\n" +
                        "- UI: HTML/CSS/Bootstrap\n" +
                        "- Database: MySQL\n" +
                        ", startDate=2023-12-01\n" +
                        ", endDate=null\n" +
                        ", status=Active\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/ResumeBuilder\n" +
                        "}, Project{id=8\n" +
                        ", name='Travel Agency\n" +
                        ", description='I designed a windows application using Java 17 and JavaFX as UI framework.\n" +
                        "This app has the capabilities of booking flights, and seeing the history of the tickets bought by the user.\n" +
                        "\n" +
                        "Stack:\n" +
                        "- Back-End: Java 17\n" +
                        "- UI: JavaFX\n" +
                        "- Database: MySQL\n" +
                        ", startDate=2023-04-17\n" +
                        ", endDate=2023-08-09\n" +
                        ", status=Completed\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/TravelAgency\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "patents:[Patent{id=1\n" +
                        ", title='Curtain Relay\n" +
                        ", registrationNumber='A1B2C3\n" +
                        ", registrationDate=2024-03-05\n" +
                        ", referenceLink='null\n" +
                        ", description='A simple product that opens and blinds the curtains\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "presentations:[Presentation{id=1\n" +
                        ", title='Introduction of new product in company\n" +
                        ", date=2024-05-19\n" +
                        ", referenceLink='null\n" +
                        ", description='In this event, I introduced my new product in the annual celebration of my company\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "awards:[]\n" +
                        "_________________________________________\n" +
                        "publications:[Publication{id=1\n" +
                        ", title='Clean code book summary chapter by chapter\n" +
                        ", author='Mohammadmahdi Farrokhy\n" +
                        ", publisher='Mohammadmahdi Farrokhy\n" +
                        ", date=2023-09-26\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/CleanCodeBook\n" +
                        ", description='A summarized version of Clean Code book by Robert C. Martin in a simple language and practical code examples\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "volunteerActivities:[VolunteerActivity{id=1\n" +
                        ", title='Scientific Association of the Faculty of Computer Engineering\n" +
                        ", year=2019\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "memberships:[Membership{id=1\n" +
                        ", title='Scientific Association of the Faculty of Computer Engineering\n" +
                        ", date=2019-01-01\n" +
                        "}, Membership{id=2\n" +
                        ", title='Scientific Association of the Faculty of Computer Engineering\n" +
                        ", date=2019-01-01\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "hobbies:[Hobby{id=1\n" +
                        ", title='Escape Room\n" +
                        "}, Hobby{id=2\n" +
                        ", title='Escape Room\n" +
                        "}]\n" +
                        "_________________________________________\n";

        String actualResult = "";
        if (user != null)
            actualResult += "user:" + user + "\n_________________________________________\n";
        else
            actualResult += "user:[]\n_________________________________________\n";

        if (contactInformation != null)
            actualResult += contactInformation + "\n_________________________________________\n";
        else
            actualResult += "contactInformation:[]\n_________________________________________\n";

        if (educations != null)
            actualResult += educations + "\n_________________________________________\n";
        else
            actualResult += "educations:[]" + "\n_________________________________________\n";

        if (teachingAssistance != null)
            actualResult += "teachingAssistance:" + teachingAssistance + "\n_________________________________________\n";
        else
            actualResult += "teachingAssistance:[]" + "\n_________________________________________\n";

        if (jobExperiences != null)
            actualResult += "jobExperiences:" + jobExperiences + "\n_________________________________________\n";
        else
            actualResult += "jobExperiences:[]" + "\n_________________________________________\n";

        if (formerColleagues != null)
            actualResult += "formerColleagues:" + formerColleagues + "\n_________________________________________\n";
        else
            actualResult += "formerColleagues:[]" + "\n_________________________________________\n";

        if (researches != null)
            actualResult += "researches:" + researches + "\n_________________________________________\n";
        else
            actualResult += "researches:[]" + "\n_________________________________________\n";

        if (courses != null)
            actualResult += "courses:" + courses + "\n_________________________________________\n";
        else
            actualResult += "courses:[]" + "\n_________________________________________\n";

        if (hardSkills != null)
            actualResult += "hardSkills:" + hardSkills + "\n_________________________________________\n";
        else
            actualResult += "hardSkills:[]" + "\n_________________________________________\n";

        if (softSkills != null)
            actualResult += "softSkills:" + softSkills + "\n_________________________________________\n";
        else
            actualResult += "softSkills:[]" + "\n_________________________________________\n";

        if (languages != null)
            actualResult += "languages:" + languages + "\n_________________________________________\n";
        else
            actualResult += "languages:[]" + "\n_________________________________________\n";

        if (projects != null)
            actualResult += "projects:" + projects + "\n_________________________________________\n";
        else
            actualResult += "projects:[]" + "\n_________________________________________\n";

        if (patents != null)
            actualResult += "patents:" + patents + "\n_________________________________________\n";
        else
            actualResult += "patents:[]" + "\n_________________________________________\n";

        if (presentations != null)
            actualResult += "presentations:" + presentations + "\n_________________________________________\n";
        else
            actualResult += "presentations:[]" + "\n_________________________________________\n";

        if (awards != null)
            actualResult += "awards:" + awards + "\n_________________________________________\n";
        else
            actualResult += "awards:[]" + "\n_________________________________________\n";

        if (publications != null)
            actualResult += "publications:" + publications + "\n_________________________________________\n";
        else
            actualResult += "publications:[]" + "\n_________________________________________\n";

        if (volunteerActivities != null)
            actualResult += "volunteerActivities:" + volunteerActivities + "\n_________________________________________\n";
        else
            actualResult += "volunteerActivities:[]" + "\n_________________________________________\n";

        if (memberships != null)
            actualResult += "memberships:" + memberships + "\n_________________________________________\n";
        else
            actualResult += "memberships:[]" + "\n_________________________________________\n";

        if (hobbies != null)
            actualResult += "hobbies:" + hobbies + "\n_________________________________________\n";
        else
            actualResult += "hobbies:[]" + "\n_________________________________________\n";

        if (actualResult.equals(expectedResult))
            System.out.println("Passed");
        else
            System.out.println(actualResult);
    }

    public static void deleteResumeEducation(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        Education education = resumeDAO.fetchSection(resumeId, Education.class).stream().findFirst().get();
        resumeDAO.deleteSection(education);
        System.out.println("Education " + education.getId() + " deleted\n");
    }

    public static void deleteResumeJobExperience(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        JobExperience jobExperience = resumeDAO.fetchSection(resumeId, JobExperience.class).stream().findFirst().get();
        resumeDAO.deleteSection(jobExperience);
        System.out.println("Job experience " + jobExperience.getId() + " deleted\n");
    }

    public static void deleteResumeTeachingAssistance(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        TeachingAssistance teachingAssistance = resumeDAO.fetchSection(resumeId, TeachingAssistance.class).stream().findFirst().get();
        resumeDAO.deleteSection(teachingAssistance);
        System.out.println("Teaching assistance " + teachingAssistance.getId() + " deleted\n");
    }

    public static void deleteResumeContactMethod(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        List<ContactMethod> contactInformation = resumeDAO.fetchSection(resumeId, ContactMethod.class);
        ContactMethod contactMethod = contactInformation.stream().filter(cm -> cm.getType() == ContactType.Address).findFirst().get();
        resumeDAO.deleteSection(contactMethod);
        System.out.println("Contact method " + contactMethod.getId() + " deleted\n");
    }

    public static void deleteResumeCourse(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        List<Course> courses = resumeDAO.fetchSection(resumeId, Course.class);
        Course course = courses.stream().findFirst().get();
        resumeDAO.deleteSection(course);
        System.out.println("Course " + course.getId() + " deleted\n");
    }

    public static void deleteResumeProject(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        Project project = resumeDAO.fetchSection(resumeId, Project.class).stream().findFirst().get();
        resumeDAO.deleteSection(project);
        System.out.println("Project " + project.getId() + " deleted\n");
    }

    public static void deleteResumeLanguage(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        Language language = resumeDAO.fetchSection(resumeId, Language.class).stream().findFirst().get();
        resumeDAO.deleteSection(language);
        System.out.println("Language " + language.getId() + " deleted\n");
    }

    public static void deleteResumeSoftSkill(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        SoftSkill softSkill = resumeDAO.fetchSection(resumeId, SoftSkill.class).stream().findFirst().get();
        resumeDAO.deleteSection(softSkill);
        System.out.println("Soft skill " + softSkill.getId() + " deleted\n");
    }

    public static void deleteResumeHardSkill(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        HardSkill hardSkill = resumeDAO.fetchSection(resumeId, HardSkill.class).stream().findFirst().get();
        resumeDAO.deleteSection(hardSkill);
        System.out.println("Hard skill " + hardSkill.getId() + " deleted\n");
    }

    public static void deleteResumeResearch(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        Research research = resumeDAO.fetchSection(resumeId, Research.class).stream().findFirst().get();
        resumeDAO.deleteSection(research);
        System.out.println("Research " + research.getId() + " deleted\n");
    }

    public static void deleteResumeFormerColleague(ResumeDAO resumeDAO) {
        Integer resumeId = 12;
        FormerColleague formerColleague = resumeDAO.fetchSection(resumeId, FormerColleague.class).stream().findFirst().get();
        resumeDAO.deleteSection(formerColleague);
        System.out.println("Former colleague " + formerColleague.getId() + " deleted\n");
    }

    public static void updateResumeSectionsInDatabase(ResumeDAO resumeDAO) {
        Integer resumeId = 13;
        Resume resume = resumeDAO.findById(resumeId);
        PersonalInformation user = resume.getPersonalInformation();
        user.setFirstName("Mohammadmahdi");
        resumeDAO.updateSection(user);
        System.out.println("User updated\n");

        List<ContactMethod> contactInformation = resumeDAO.fetchSection(resumeId, ContactMethod.class);
        ContactMethod contactMethod = contactInformation.stream().filter(e1 -> e1.getType() == ContactType.Email).findFirst().get();
        contactMethod.setValue("mmahdifarrokhy@gmail.com");
        resumeDAO.updateSection(contactMethod);
        System.out.println("Email updated\n");

        Education education = resumeDAO.fetchSection(resumeId, Education.class).stream().findFirst().get();
        education.setGpa(16);
        resumeDAO.updateSection(education);
        System.out.println("Education updated\n");

        TeachingAssistance teachingAssistance = resumeDAO.fetchSection(resumeId, TeachingAssistance.class).stream().findFirst().get();
        teachingAssistance.setTitle("Advanced Programming (Java)");
        resumeDAO.updateSection(teachingAssistance);
        System.out.println("Teaching assistance updated\n");

        JobExperience jobExperience = resumeDAO.fetchSection(resumeId, JobExperience.class).stream().findFirst().get();
        jobExperience.setTitle("Software developer and consultant");
        resumeDAO.updateSection(jobExperience);
        System.out.println("Job experience updated\n");

        FormerColleague formerColleague = resumeDAO.fetchSection(resumeId, FormerColleague.class).stream().findFirst().get();
        formerColleague.setPhoneNumber("09987654321");
        resumeDAO.updateSection(formerColleague);
        System.out.println("Former colleague updated\n");

        Research research = resumeDAO.fetchSection(resumeId, Research.class).stream().findFirst().get();
        research.setTitle("Summarizing Clean Code book (Robert C. Martin)");
        resumeDAO.updateSection(research);
        System.out.println("Research updated\n");

        Course course = resumeDAO.fetchSection(resumeId, Course.class).stream().findFirst().get();
        course.setInstitute("Se7en Learn");
        resumeDAO.updateSection(course);
        System.out.println("Course updated\n");

        HardSkill hardSkill = resumeDAO.fetchSection(resumeId, HardSkill.class).stream().findFirst().get();
        hardSkill.setLevel(HardSkillLevel.Intermediate);
        resumeDAO.updateSection(hardSkill);
        System.out.println("Hard skill updated\n");

        SoftSkill softSkill = resumeDAO.fetchSection(resumeId, SoftSkill.class).stream().findFirst().get();
        softSkill.setTitle("Instruction");
        resumeDAO.updateSection(softSkill);
        System.out.println("Soft skill updated\n");

        Language language = resumeDAO.fetchSection(resumeId, Language.class).stream().findFirst().get();
        language.setSpeakingLevel(LanguageLevel.Basic);
        resumeDAO.updateSection(language);
        System.out.println("Language updated\n");

        Project project = resumeDAO.fetchSection(resumeId, Project.class).stream().findFirst().get();
        project.setName("Resume Builder Web Application");
        resumeDAO.updateSection(project);
        System.out.println("Project updated\n");
    }

    public static void findResumeInDatabase(ResumeDAO resumeDAO) {
        int resumeId = 13;
        Resume resume = resumeDAO.findById(resumeId);
        PersonalInformation user = resume.getPersonalInformation();
        List<ContactMethod> contactInformation = resumeDAO.fetchSection(resumeId, ContactMethod.class);
        List<Education> educations = resumeDAO.fetchSection(resumeId, Education.class);
        List<TeachingAssistance> teachingAssistance = resumeDAO.fetchSection(resumeId, TeachingAssistance.class);
        List<JobExperience> jobExperiences = resumeDAO.fetchSection(resumeId, JobExperience.class);
        List<FormerColleague> formerColleagues = resumeDAO.fetchSection(resumeId, FormerColleague.class);
        List<Research> researches = resumeDAO.fetchSection(resumeId, Research.class);
        List<Course> courses = resumeDAO.fetchSection(resumeId, Course.class);
        List<HardSkill> hardSkills = resumeDAO.fetchSection(resumeId, HardSkill.class);
        List<SoftSkill> softSkills = resumeDAO.fetchSection(resumeId, SoftSkill.class);
        List<Language> languages = resumeDAO.fetchSection(resumeId, Language.class);
        List<Project> projects = resumeDAO.fetchSection(resumeId, Project.class);
        List<Patent> patents = resumeDAO.fetchSection(resumeId, Patent.class);
        List<Presentation> presentations = resumeDAO.fetchSection(resumeId, Presentation.class);
        List<Award> awards = resumeDAO.fetchSection(resumeId, Award.class);
        List<Publication> publications = resumeDAO.fetchSection(resumeId, Publication.class);
        List<VolunteerActivity> volunteerActivities = resumeDAO.fetchSection(resumeId, VolunteerActivity.class);
        List<Membership> memberships = resumeDAO.fetchSection(resumeId, Membership.class);
        List<Hobby> hobbies = resumeDAO.fetchSection(resumeId, Hobby.class);

        String expectedResult =
                "user:User{id=13\n" +
                        ", firstName='Mohammad Mahdi\n" +
                        ", lastName='Farrokhy\n" +
                        ", phoneNumber='09017743009\n" +
                        ", userDetail=UserDetail{id=13\n" +
                        ", maritalStatus=Single\n" +
                        ", gender=Male\n" +
                        ", militaryServiceStatus=Exempted\n" +
                        ", birthDate=1999-07-29\n" +
                        ", foreigner=false\n" +
                        ", disabilityType=None\n" +
                        "}}\n" +
                        "_________________________________________\n" +
                        "[ContactMethod{id=39\n" +
                        ", type=Address\n" +
                        ", value='Tehran, Pardis County\n" +
                        "}, ContactMethod{id=40\n" +
                        ", type=Email\n" +
                        ", value='mmahdifarrokhy@gmail.com\n" +
                        "}, ContactMethod{id=41\n" +
                        ", type=LinkedIn\n" +
                        ", value='https://www.linkedin.com/in/mmahdi-farrokhy/\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "[Education{id=13\n" +
                        ", degreeLevel=Bachelor\n" +
                        ", major=Computer Engineering\n" +
                        ", university='Semnan University\n" +
                        ", gpa=16.0\n" +
                        ", startYear=2017\n" +
                        ", endYear=2021\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "teachingAssistance:[TeachingAssistance{id=8\n" +
                        ", title='Advanced Programming (Java)\n" +
                        ", university='Semnan University\n" +
                        ", startDate=2019-01-01\n" +
                        ", endDate=2019-06-30\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "jobExperiences:[JobExperience{id=14\n" +
                        ", title='Software Developer And Consultant\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Junior\n" +
                        ", companyName='Negar Andishgan Co. Ltd.\n" +
                        ", description='Developing and refactoring NrSign.EMG medical test software\n" +
                        ", startDate=2023-01-07\n" +
                        ", endDate=null\n" +
                        ", status=Occupied\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}, JobExperience{id=15\n" +
                        ", title='C# Developer\n" +
                        ", category=Software Development\n" +
                        ", seniorityLevel=Junior\n" +
                        ", companyName='Zarvan Stun Khodro\n" +
                        ", description='Developing automotive software Diag and Remap\n" +
                        ", startDate=2022-04-17\n" +
                        ", endDate=2022-12-21\n" +
                        ", status=Finished\n" +
                        ", location=Location{id=1\n" +
                        ", cityName=Tehran\n" +
                        ", countryId=16\n" +
                        "}\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "formerColleagues:[FormerColleague{id=23\n" +
                        ", fullName='Ehsan Arbabi\n" +
                        ", position='Manager\n" +
                        ", organizationName='Negar Andishgan Co. Ltd.\n" +
                        ", phoneNumber='09123456789\n" +
                        "}, FormerColleague{id=24\n" +
                        ", fullName='Mohammad Hossein Ommi\n" +
                        ", position='CEO\n" +
                        ", organizationName='Zarvan Stun Khodro\n" +
                        ", phoneNumber='09123456789\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "researches:[Research{id=7\n" +
                        ", title='Researching about Clean Code\n" +
                        ", publisher='Mohammad Mahdi Farrokhy\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/CleanCodeBook\n" +
                        ", date=2022-07-01\n" +
                        ", description='In this repository you can read the simplified summary of Clean Code book by Robert C. Martin in separated chapters.\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "courses:[Course{id=26\n" +
                        ", name='Java Expert\n" +
                        ", institute='7Learn\n" +
                        ", credentialId='https://7learn.com/crt?h=bdP9hiSTF4\n" +
                        "}, Course{id=27\n" +
                        ", name='Spring Boot - Chad Darby\n" +
                        ", institute='Udemy\n" +
                        ", credentialId='null\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "hardSkills:[HardSkill{id=23\n" +
                        ", type=Spring Boot\n" +
                        ", level=Beginner\n" +
                        "}, HardSkill{id=24\n" +
                        ", type=Java\n" +
                        ", level=Intermediate\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "softSkills:[SoftSkill{id=13\n" +
                        ", title='Teaching\n" +
                        "}, SoftSkill{id=14\n" +
                        ", title='Presentation\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "languages:[Language{id=19\n" +
                        ", name=German\n" +
                        ", speakingLevel=Pre Intermediate\n" +
                        ", writingLevel=Pre Intermediate\n" +
                        ", readingLevel=Pre Intermediate\n" +
                        ", listeningLevel=Pre Intermediate\n" +
                        ", researchingLevel=Pre Intermediate\n" +
                        "}, Language{id=20\n" +
                        ", name=English\n" +
                        ", speakingLevel=Intermediate\n" +
                        ", writingLevel=Intermediate\n" +
                        ", readingLevel=Upper Intermediate\n" +
                        ", listeningLevel=Upper Intermediate\n" +
                        ", researchingLevel=Upper Intermediate\n" +
                        "}, Language{id=21\n" +
                        ", name=Persian\n" +
                        ", speakingLevel=Native\n" +
                        ", writingLevel=Native\n" +
                        ", readingLevel=Native\n" +
                        ", listeningLevel=Native\n" +
                        ", researchingLevel=Native\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "projects:[Project{id=7\n" +
                        ", name='Resume Builder\n" +
                        ", description='I designed a resume builder web application using Spring Boot.\n" +
                        "This app has the capabilities of creating a new resume, editing an existing resume and removing a resume from profile.\n" +
                        "\n" +
                        "Stack:\n" +
                        "- Back-End: Java/Spring Boot\n" +
                        "- UI: HTML/CSS/Bootstrap\n" +
                        "- Database: MySQL\n" +
                        ", startDate=2023-12-01\n" +
                        ", endDate=null\n" +
                        ", status=Active\n" +
                        ", referenceLink='https://github.com/mmahdi-farrokhy/ResumeBuilder\n" +
                        "}]\n" +
                        "_________________________________________\n" +
                        "patents:[]\n" +
                        "_________________________________________\n" +
                        "presentations:[]\n" +
                        "_________________________________________\n" +
                        "awards:[]\n" +
                        "_________________________________________\n" +
                        "publications:[]\n" +
                        "_________________________________________\n" +
                        "volunteerActivities:[]\n" +
                        "_________________________________________\n" +
                        "memberships:[]\n" +
                        "_________________________________________\n" +
                        "hobbies:[]\n" +
                        "_________________________________________\n";

        String actualResult = "";
        if (user != null)
            actualResult += "user:" + user + "\n_________________________________________\n";
        else
            actualResult += "user:[]\n_________________________________________\n";

        if (contactInformation != null)
            actualResult += contactInformation + "\n_________________________________________\n";
        else
            actualResult += "contactInformation:[]\n_________________________________________\n";

        if (educations != null)
            actualResult += educations + "\n_________________________________________\n";
        else
            actualResult += "educations:[]" + "\n_________________________________________\n";

        if (teachingAssistance != null)
            actualResult += "teachingAssistance:" + teachingAssistance + "\n_________________________________________\n";
        else
            actualResult += "teachingAssistance:[]" + "\n_________________________________________\n";

        if (jobExperiences != null)
            actualResult += "jobExperiences:" + jobExperiences + "\n_________________________________________\n";
        else
            actualResult += "jobExperiences:[]" + "\n_________________________________________\n";

        if (formerColleagues != null)
            actualResult += "formerColleagues:" + formerColleagues + "\n_________________________________________\n";
        else
            actualResult += "formerColleagues:[]" + "\n_________________________________________\n";

        if (researches != null)
            actualResult += "researches:" + researches + "\n_________________________________________\n";
        else
            actualResult += "researches:[]" + "\n_________________________________________\n";

        if (courses != null)
            actualResult += "courses:" + courses + "\n_________________________________________\n";
        else
            actualResult += "courses:[]" + "\n_________________________________________\n";

        if (hardSkills != null)
            actualResult += "hardSkills:" + hardSkills + "\n_________________________________________\n";
        else
            actualResult += "hardSkills:[]" + "\n_________________________________________\n";

        if (softSkills != null)
            actualResult += "softSkills:" + softSkills + "\n_________________________________________\n";
        else
            actualResult += "softSkills:[]" + "\n_________________________________________\n";

        if (languages != null)
            actualResult += "languages:" + languages + "\n_________________________________________\n";
        else
            actualResult += "languages:[]" + "\n_________________________________________\n";

        if (projects != null)
            actualResult += "projects:" + projects + "\n_________________________________________\n";
        else
            actualResult += "projects:[]" + "\n_________________________________________\n";

        if (patents != null)
            actualResult += "patents:" + patents + "\n_________________________________________\n";
        else
            actualResult += "patents:[]" + "\n_________________________________________\n";

        if (presentations != null)
            actualResult += "presentations:" + presentations + "\n_________________________________________\n";
        else
            actualResult += "presentations:[]" + "\n_________________________________________\n";

        if (awards != null)
            actualResult += "awards:" + awards + "\n_________________________________________\n";
        else
            actualResult += "awards:[]" + "\n_________________________________________\n";

        if (publications != null)
            actualResult += "publications:" + publications + "\n_________________________________________\n";
        else
            actualResult += "publications:[]" + "\n_________________________________________\n";

        if (volunteerActivities != null)
            actualResult += "volunteerActivities:" + volunteerActivities + "\n_________________________________________\n";
        else
            actualResult += "volunteerActivities:[]" + "\n_________________________________________\n";

        if (memberships != null)
            actualResult += "memberships:" + memberships + "\n_________________________________________\n";
        else
            actualResult += "memberships:[]" + "\n_________________________________________\n";

        if (hobbies != null)
            actualResult += "hobbies:" + hobbies + "\n_________________________________________\n";
        else
            actualResult += "hobbies:[]" + "\n_________________________________________\n";

        if (actualResult.equals(expectedResult))
            System.out.println("Passed");
        else
            System.out.println(actualResult);
    }

    public static void addResume(ResumeDAO resumeDAO) {
        System.out.println("Creating resume");
        Resume resume = new Resume();
        resume.setPersonalInformation(createUser());

        addContactMethods(resume);
        addSummary(resume);
        addEducation(resume);
        addTeachingAssistance(resume);
        addJobExperience(resume);
        addFormerColleague(resume);
        addResearch(resume);
        addCourse(resume);
        addHardSkills(resume);
        addSoftSkills(resume);
        addLanguages(resume);
        addProjects(resume);

        System.out.println("Saving resume");
        resumeDAO.save(resume);
        System.out.println("Resume saved");
    }

    private static void addProjects(Resume resume) {
        Project project1 = createProject(resume);
        resume.addSection(project1);
    }

    private static void addLanguages(Resume resume) {
        Language language1 = createLanguage(resume, LanguageName.German, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate);
        Language language2 = createLanguage(resume, LanguageName.English, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate);
        Language language3 = createLanguage(resume, LanguageName.Persian, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native);
        resume.addSection(language1);
        resume.addSection(language2);
        resume.addSection(language3);
    }

    private static void addSoftSkills(Resume resume) {
        SoftSkill softSkill1 = createSoftSkill(resume, "Teaching");
        SoftSkill softSkill2 = createSoftSkill(resume, "Presentation");
        resume.addSection(softSkill1);
        resume.addSection(softSkill2);
    }

    private static void addHardSkills(Resume resume) {
        HardSkill hardSkill1 = createHardSkill(resume, HardSkillType.Spring_Boot, HardSkillLevel.Beginner);
        HardSkill hardSkill2 = createHardSkill(resume, HardSkillType.Java, HardSkillLevel.Intermediate);
        resume.addSection(hardSkill1);
        resume.addSection(hardSkill2);
    }

    private static void addCourse(Resume resume) {
        Course course1 = createCourse(resume, "Java Expert", "7Learn", "https://7learn.com/crt?h=bdP9hiSTF4");
        Course course2 = createCourse(resume, "Spring Boot - Chad Darby", "Udemy", null);
        resume.addSection(course1);
        resume.addSection(course2);
    }

    private static void addResearch(Resume resume) {
        Research research1 = createResearch(resume);
        resume.addSection(research1);
    }

    private static void addFormerColleague(Resume resume) {
        FormerColleague formerColleague1 = createFormerColleague1(resume);
        FormerColleague formerColleague2 = createFormerColleague2(resume);
        resume.addSection(formerColleague1);
        resume.addSection(formerColleague2);
    }

    private static void addJobExperience(Resume resume) {
        JobExperience jobExperience1 = createJobExperience1(resume);
        JobExperience jobExperience2 = createJobExperience2(resume);
        resume.addSection(jobExperience1);
        resume.addSection(jobExperience2);
    }

    private static void addTeachingAssistance(Resume resume) {
        TeachingAssistance teachingAssistance1 = createTeachingAssistance(resume);
        resume.addSection(teachingAssistance1);
    }

    private static void addEducation(Resume resume) {
        Education education1 = createEducation(resume);
        resume.addSection(education1);
    }

    private static void addSummary(Resume resume) {
        Summary summary = createSummary();
        resume.setSummary(summary);
    }

    private static void addContactMethods(Resume resume) {
        ContactMethod contactMethod1 = createContactMethod(resume, ContactType.Address, "Tehran, Pardis County");
        ContactMethod contactMethod2 = createContactMethod(resume, ContactType.Email, "mmahdifarrokhy@gmail.com");
        ContactMethod contactMethod3 = createContactMethod(resume, ContactType.LinkedIn, "https://www.linkedin.com/in/mmahdi-farrokhy/");
        resume.addSection(contactMethod1);
        resume.addSection(contactMethod2);
        resume.addSection(contactMethod3);
    }

    private static Project createProject(Resume resume) {
        Project project = new Project();
        project.setName("Resume Builder");
        project.setDescription("I designed a resume builder web application using Spring Boot.\n" +
                "This app has the capabilities of creating a new resume, editing an existing resume and removing a resume from profile.\n" +
                "\n" +
                "Stack:\n" +
                "- Back-End: Java/Spring Boot\n" +
                "- UI: HTML/CSS/Bootstrap\n" +
                "- Database: MySQL");

        project.setStartDate(LocalDate.of(2023, 12, 1));
        project.setStatus(ProjectStatus.Active);
        project.setReferenceLink("https://github.com/mmahdi-farrokhy/ResumeBuilder");
        project.setResume(resume);
        return project;
    }

    private static Project createProject2(Resume resume) {
        Project project = new Project();
        project.setName("Travel Agency");
        project.setDescription("I designed a windows application using Java 17 and JavaFX as UI framework.\n" +
                "This app has the capabilities of booking flights, and seeing the history of the tickets bought by the user.\n" +
                "\n" +
                "Stack:\n" +
                "- Back-End: Java 17\n" +
                "- UI: JavaFX\n" +
                "- Database: MySQL");

        project.setStartDate(LocalDate.of(2023, 4, 17));
        project.setEndDate(LocalDate.of(2023, 8, 9));
        project.setStatus(ProjectStatus.Completed);
        project.setReferenceLink("https://github.com/mmahdi-farrokhy/TravelAgency");
        project.setResume(resume);
        return project;
    }

    private static Language createLanguage(Resume resume, LanguageName name, LanguageLevel speaking, LanguageLevel writing, LanguageLevel listening, LanguageLevel reading, LanguageLevel research) {
        Language language = new Language();
        language.setName(name);
        language.setSpeakingLevel(speaking);
        language.setWritingLevel(writing);
        language.setListeningLevel(listening);
        language.setReadingLevel(reading);
        language.setResearchingLevel(research);
        language.setResume(resume);
        return language;
    }

    private static SoftSkill createSoftSkill(Resume resume, String title) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setTitle(title);
        softSkill.setResume(resume);
        return softSkill;
    }

    private static HardSkill createHardSkill(Resume resume, HardSkillType type, HardSkillLevel level) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setType(type);
        hardSkill.setLevel(level);
        hardSkill.setResume(resume);
        return hardSkill;
    }

    private static Course createCourse(Resume resume, String name, String institute, String id) {
        Course course = new Course();
        course.setName(name);
        course.setInstitute(institute);
        course.setCredentialId(id);
        course.setResume(resume);
        return course;
    }

    private static Research createResearch(Resume resume) {
        Research research = new Research();
        research.setTitle("Researching about Clean Code");
        research.setPublisher("Mohammad Mahdi Farrokhy");
        research.setReferenceLink("https://github.com/mmahdi-farrokhy/CleanCodeBook");
        research.setDate(LocalDate.of(2022, 7, 1));
        research.setDescription("In this repository you can read the simplified summary of Clean Code book by Robert C. Martin in separated chapters.");
        research.setResume(resume);
        return research;
    }

    private static Research createResearch2(Resume resume) {
        Research research = new Research();
        research.setTitle("Convolutional Neural Networks");
        research.setPublisher("Mohammad Mahdi Farrokhy");
        research.setReferenceLink("https://github.com/mmahdi-farrokhy/Convolutional_Neural_Network");
        research.setDate(LocalDate.of(2022, 11, 5));
        research.setDescription("I researched about CNN which is a subject under Machine Learning. And also developed a 'hand written digit recognition system' using VHDL");
        research.setResume(resume);
        return research;
    }

    private static FormerColleague createFormerColleague2(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Mohammad Hossein Ommi");
        formerColleague.setPosition("CEO");
        formerColleague.setOrganizationName("Zarvan Stun Khodro");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    private static FormerColleague createFormerColleague1(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Ehsan Arbabi");
        formerColleague.setPosition("Manager");
        formerColleague.setOrganizationName("Negar Andishgan Co. Ltd.");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    private static JobExperience createJobExperience1(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("Software Developer And Consultant");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Negar Andishgan Co. Ltd.");
        jobExperience.setDescription("Developing and refactoring NrSign.EMG medical test software");
        jobExperience.setStartDate(LocalDate.of(2023, 1, 7));
        jobExperience.setStatus(JobStatus.Occupied);
        jobExperience.setLocation(createLocation());
        jobExperience.setResume(resume);
        return jobExperience;
    }

    private static JobExperience createJobExperience2(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("C# Developer");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Zarvan Stun Khodro");
        jobExperience.setDescription("Developing automotive software Diag and Remap");
        jobExperience.setStartDate(LocalDate.of(2022, 4, 17));
        jobExperience.setEndDate(LocalDate.of(2022, 12, 21));
        jobExperience.setStatus(JobStatus.Finished);
        jobExperience.setLocation(createLocation());
        jobExperience.setResume(resume);
        return jobExperience;
    }

    private static JobExperience createJobExperience3(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("Java/Spring Developer");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Mid_Level);
        jobExperience.setCompanyName("Blu Bank");
        jobExperience.setDescription("Developing new sections in Blu environment");
        jobExperience.setStartDate(LocalDate.of(2024, 4, 2));
        jobExperience.setStatus(JobStatus.Occupied);
        jobExperience.setLocation(createLocation());
        jobExperience.setResume(resume);
        return jobExperience;
    }

    private static Location createLocation() {
        Location location = new Location();
        location.setId(1);
        location.setCityName(City.Tehran);
        location.setCountryId(location.getCityName().getCountryId());
        return location;
    }

    private static TeachingAssistance createTeachingAssistance(Resume resume) {
        TeachingAssistance teachingAssistance = new TeachingAssistance();
        teachingAssistance.setTitle("Advanced Programming (Java)");
        teachingAssistance.setUniversity("Semnan University");
        teachingAssistance.setStartDate(LocalDate.of(2019, 1, 1));
        teachingAssistance.setEndDate(LocalDate.of(2019, 6, 30));
        teachingAssistance.setResume(resume);
        return teachingAssistance;
    }

    private static TeachingAssistance createTeachingAssistance2(Resume resume) {
        TeachingAssistance teachingAssistance = new TeachingAssistance();
        teachingAssistance.setTitle("Digital Electronic (MOSFET Transistors)");
        teachingAssistance.setUniversity("Semnan University");
        teachingAssistance.setStartDate(LocalDate.of(2019, 1, 1));
        teachingAssistance.setEndDate(LocalDate.of(2019, 6, 30));
        teachingAssistance.setResume(resume);
        return teachingAssistance;
    }

    private static Education createEducation(Resume resume) {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.Bachelor);
        education.setMajor(Major.Computer_Engineering);
        education.setUniversity("Semnan University");
        education.setGpa(16);
        education.setStartYear(2017);
        education.setEndYear(2021);
        education.setResume(resume);
        return education;
    }

    private static Summary createSummary() {
        Summary summary = new Summary();
        summary.setText("As a junior software developer with 19 months of hands-on experience, I thrive on turning complex challenges into\n" +
                "elegant solutions. My proficiency in C# .NET and Java allows me to create robust applications that seamlessly\n" +
                "bridge user needs with technical capabilities.\n" +
                "Passionate about clean code and agile principles, I believe in the power of organized design and development to\n" +
                "create software that stands the test of time. By adhering to best practices, I ensure that every line of code I write\n" +
                "contributes to a maintainable and scalable product.\n" +
                "In my journey, I've come to appreciate the collaborative nature of software development. I'm excited to work\n" +
                "alongside like-minded professionals who share my dedication to crafting exceptional software. Let's connect and\n" +
                "explore how we can drive innovation through code, design, and a commitment to continuous learning.");
        return summary;
    }

    private static ContactMethod createContactMethod(Resume resume, ContactType type, String value) {
        ContactMethod contactMethod = new ContactMethod();
        contactMethod.setType(type);
        contactMethod.setValue(value);
        contactMethod.setResume(resume);
        return contactMethod;
    }

    private static PersonalInformation createUser() {
        PersonalInformation user = new PersonalInformation();
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setPhoneNumber("09017743009");
        return user;
    }

    private static Hobby createHobby(Resume resume, String title) {
        Hobby hobby = new Hobby();
        hobby.setTitle(title);
        hobby.setResume(resume);
        return hobby;
    }

    private static Membership createMembership(Resume resume, String title, LocalDate date) {
        Membership membership = new Membership();
        membership.setTitle(title);
        membership.setDate(date);
        membership.setResume(resume);
        return membership;
    }

    private static Patent createPatent(Resume resume) {
        Patent patent = new Patent();
        patent.setTitle("Curtain Relay");
        patent.setDescription("A simple product that opens and blinds the curtains");
        patent.setRegistrationDate(LocalDate.of(2024, 3, 5));
        patent.setRegistrationNumber("A1B2C3");
        patent.setResume(resume);
        return patent;
    }

    private static Presentation createPresentation(Resume resume) {
        Presentation presentation = new Presentation();
        presentation.setTitle("Introduction of new product in company");
        presentation.setDate(LocalDate.of(2024, 5, 19));
        presentation.setReferenceLink(null);
        presentation.setDescription("In this event, I introduced my new product in the annual celebration of my company");
        presentation.setResume(resume);
        return presentation;
    }

    private static Publication createPublication(Resume resume) {
        Publication publication = new Publication();
        publication.setTitle("Clean code book summary chapter by chapter");
        publication.setAuthor("Mohammadmahdi Farrokhy");
        publication.setPublisher("Mohammadmahdi Farrokhy");
        publication.setDate(LocalDate.of(2023, 9, 26));
        publication.setReferenceLink("https://github.com/mmahdi-farrokhy/CleanCodeBook");
        publication.setDescription("A summarized version of Clean Code book by Robert C. Martin in a simple language and practical code examples");
        publication.setResume(resume);
        return publication;
    }

    private static VolunteerActivity createVolunteerActivity(Resume resume, String title, int year) {
        VolunteerActivity volunteerActivity = new VolunteerActivity();
        volunteerActivity.setTitle(title);
        volunteerActivity.setYear(year);
        volunteerActivity.setResume(resume);
        return volunteerActivity;
    }

    public static void addUserToDatabase(UserDAO userDAO) {
        User user = new User();
        user.setFirstName("Mohammadmahdi");
        user.setLastName("Farrokhy");
        user.setEmail("mmahdifarrokhy@gmail.com");
        user.setPassword("12345679");
        user.setRole(UserRole.User);
        userDAO.save(user);
        System.out.println("User saved");
    }

    public static void findUserById(UserDAO userDAO) {
        Integer userId = 17;
        User user = userDAO.findById(userId).get();
        if (user != null)
            System.out.println(user);
        else
            System.out.println("User not fount");
    }

    public static void isEmailTaken(UserDAO userDAO) {
        String email = "mmahdifarrokhy@gmail.com";
        boolean emailAvailable = userDAO.existsByEmail(email);
        if (emailAvailable)
            System.out.println("Email is taken");
        else
            System.out.println("Email is not taken");
    }
}
