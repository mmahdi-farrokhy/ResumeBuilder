package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.constants.UserRole;
import com.mmf.resumeBuilder.constants.contactinformation.ContactType;
import com.mmf.resumeBuilder.constants.education.DegreeLevel;
import com.mmf.resumeBuilder.constants.education.Major;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillType;
import com.mmf.resumeBuilder.constants.job.JobCategory;
import com.mmf.resumeBuilder.constants.job.JobStatus;
import com.mmf.resumeBuilder.constants.language.LanguageLevel;
import com.mmf.resumeBuilder.constants.language.LanguageName;
import com.mmf.resumeBuilder.constants.location.City;
import com.mmf.resumeBuilder.constants.project.ProjectStatus;
import com.mmf.resumeBuilder.constants.user.detail.*;
import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.entity.resume.*;

import java.time.LocalDate;
import java.util.List;

public class DatabaseTest {
    public static User createAppUser(Resume resume) {
        User appUser = new User();
        appUser.setEmail("mmahdifarrokhy12345678@gmail.com");
        appUser.setPassword("11111111");
        appUser.setRole(UserRole.User);
        appUser.addResume(resume);
        return appUser;
    }

    public static Resume createResume() {
        Resume resume = new Resume();
        resume.setUser(new User("mmahdifarrokhy@gmail.com", "12345678", UserRole.User, List.of(resume)));
        resume.setPersonalInformation(createPersonalInformation());
        resume.addSection(createContactMethod(resume, ContactType.Email, "mmahdifarrokhy@gmail.com"));
        resume.addSection(createContactMethod(resume, ContactType.Phone_Number, "09190763415"));
        resume.addSection(createContactMethod(resume, ContactType.Address, "Tehran, Pardis County"));
        resume.setSummary(createSummary());
        resume.addSection(createEducation());
        resume.addSection(createEducation2());
        resume.addSection(createTeachingAssistance(resume));
        resume.addSection(createTeachingAssistance2(resume));
        resume.addSection(createJobExperience1(resume));
        resume.addSection(createJobExperience2(resume));
        resume.addSection(createJobExperience3(resume));
        resume.addSection(createFormerColleague1(resume));
        resume.addSection(createFormerColleague2(resume));
        resume.addSection(createResearch(resume));
        resume.addSection(createResearch2(resume));
        resume.addSection(createCourse(resume, "Java Expert", "7learn", "https://7learn.com/crt?h=bdP9hiSTF4"));
        resume.addSection(createCourse(resume, "Spring Boot - Chad Darby", "Udemy", null));
        resume.addSection(createHardSkill(resume, HardSkillType.Java, HardSkillLevel.Advanced));
        resume.addSection(createHardSkill(resume, HardSkillType.Spring_Boot, HardSkillLevel.Intermediate));
        resume.addSection(createSoftSkill(resume, "Instruction"));
        resume.addSection(createLanguage(resume, LanguageName.Persian, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native));
        resume.addSection(createLanguage(resume, LanguageName.German, LanguageLevel.Basic, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate));
        resume.addSection(createLanguage(resume, LanguageName.English, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate));
        resume.addSection(createProject(resume));
        resume.addSection(createProject2(resume));
        resume.addSection(createPresentation(resume));
        resume.addSection(createMembership(resume, "Scientific Association of the Faculty of Computer Engineering", LocalDate.of(2019, 1, 1)));
        resume.addSection(createHobby(resume, "Escape Room"));
        resume.addSection(createPatent(resume));
        resume.addSection(createVolunteerActivity(resume, "Beautime Startup Core Member", 2019));
        resume.addSection(createVolunteerActivity(resume, "Creating Clean Code Documents", 2023));
        return resume;
    }

    public static Project createProject(Resume resume) {
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

    public static Project createProject2(Resume resume) {
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

    public static Language createLanguage(Resume resume, LanguageName name, LanguageLevel speaking, LanguageLevel writing, LanguageLevel listening, LanguageLevel reading, LanguageLevel research) {
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

    public static SoftSkill createSoftSkill(Resume resume, String title) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setTitle(title);
        softSkill.setResume(resume);
        return softSkill;
    }

    public static HardSkill createHardSkill(Resume resume, HardSkillType type, HardSkillLevel level) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setType(type);
        hardSkill.setLevel(level);
        hardSkill.setResume(resume);
        return hardSkill;
    }

    public static Course createCourse(Resume resume, String name, String institute, String id) {
        Course course = new Course();
        course.setName(name);
        course.setInstitute(institute);
        course.setCredentialId(id);
        course.setResume(resume);
        return course;
    }

    public static Research createResearch(Resume resume) {
        Research research = new Research();
        research.setTitle("Researching about Clean Code");
        research.setPublisher("Mohammad Mahdi Farrokhy");
        research.setReferenceLink("https://github.com/mmahdi-farrokhy/CleanCodeBook");
        research.setDate(LocalDate.of(2023, 7, 1));
        research.setDescription("In this repository you can read the simplified summary of Clean Code book by Robert C. Martin in separated chapters.");
        research.setResume(resume);
        return research;
    }

    public static Research createResearch2(Resume resume) {
        Research research = new Research();
        research.setTitle("Convolutional Neural Networks");
        research.setPublisher("Mohammad Mahdi Farrokhy");
        research.setReferenceLink("https://github.com/mmahdi-farrokhy/Convolutional_Neural_Network");
        research.setDate(LocalDate.of(2022, 11, 5));
        research.setDescription("I researched about CNN which is a subject under Machine Learning. And also developed a 'hand written digit recognition system' using VHDL");
        research.setResume(resume);
        return research;
    }

    public static FormerColleague createFormerColleague2(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Mohammad Hossein Ommi");
        formerColleague.setPosition("CEO");
        formerColleague.setOrganizationName("Zarvan Stun Khodro");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    public static FormerColleague createFormerColleague1(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Ehsan Arbabi");
        formerColleague.setPosition("Manager");
        formerColleague.setOrganizationName("Negar Andishgan Co. Ltd.");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    public static JobExperience createJobExperience1(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("Software Developer And Consultant");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Negar Andishgan Co. Ltd.");
        jobExperience.setDescription("""
                I worked as a software developer on the EMG project of this company known as NrSign.EMG, debugged it, refactored some parts and added some new features to it.
                Finally, I added the software connection through the network and TCP protocol to this software, which received almost 2.4 megabytes of data per second from the hardware with a sampling rate of 128,000 samples per second, with the size of each sample being 19 bytes. And I process, decode and draw it in the software.""");
        jobExperience.setStartDate(LocalDate.of(2023, 1, 7));
        jobExperience.setStatus(JobStatus.Occupied);
        jobExperience.setLocation(createLocation(City.Pardis));
        jobExperience.setResume(resume);
        return jobExperience;
    }

    public static JobExperience createJobExperience2(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("C# Developer");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Zarvan Stun Khodro");
        jobExperience.setDescription("""
                This company has 2 software called MasterDiag and Promap, which are car diagnostics and remaps tools. I added a few new features to each of them to connect to the ECU of different cars.
                I also developed the capability of connecting to ECUs with CAN protocol for the company's Promap software.""");
        jobExperience.setStartDate(LocalDate.of(2022, 4, 17));
        jobExperience.setEndDate(LocalDate.of(2022, 12, 21));
        jobExperience.setStatus(JobStatus.Finished);
        jobExperience.setLocation(createLocation(City.Tehran));
        jobExperience.setResume(resume);
        return jobExperience;
    }

    public static JobExperience createJobExperience3(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("Java/Spring Developer");
        jobExperience.setCategory(JobCategory.Software_Development);
        jobExperience.setSeniorityLevel(SeniorityLevel.Mid_Level);
        jobExperience.setCompanyName("Blu Bank");
        jobExperience.setDescription("Developing new sections in Blu environment");
        jobExperience.setStartDate(LocalDate.of(2024, 4, 2));
        jobExperience.setStatus(JobStatus.Occupied);
        jobExperience.setLocation(createLocation(City.Tehran));
        jobExperience.setResume(resume);
        return jobExperience;
    }

    public static Location createLocation(City city) {
        Location location = new Location();
        location.setId(1);
        location.setCityName(city);
        location.setCountryId(location.getCityName().getCountryId());
        return location;
    }

    public static TeachingAssistance createTeachingAssistance(Resume resume) {
        TeachingAssistance teachingAssistance = new TeachingAssistance();
        teachingAssistance.setTitle("Advanced Programming (Java)");
        teachingAssistance.setUniversity("Semnan University");
        teachingAssistance.setStartDate(LocalDate.of(2019, 1, 1));
        teachingAssistance.setEndDate(LocalDate.of(2019, 6, 30));
        teachingAssistance.setResume(resume);
        return teachingAssistance;
    }

    public static TeachingAssistance createTeachingAssistance2(Resume resume) {
        TeachingAssistance teachingAssistance = new TeachingAssistance();
        teachingAssistance.setTitle("Digital Electronic (MOSFET Transistors)");
        teachingAssistance.setUniversity("Semnan University");
        teachingAssistance.setStartDate(LocalDate.of(2019, 1, 1));
        teachingAssistance.setEndDate(LocalDate.of(2019, 6, 30));
        teachingAssistance.setResume(resume);
        return teachingAssistance;
    }

    public static Education createEducation() {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.Bachelor);
        education.setMajor(Major.Computer_Engineering);
        education.setUniversity("Semnan University");
        education.setGpa(16);
        education.setStartYear(2017);
        education.setEndYear(2021);
        return education;
    }

    public static Education createEducation2() {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.Master);
        education.setMajor(Major.Software_Engineering);
        education.setUniversity("Amir Kabir University of Technology (Tehran Polytechnic)");
        education.setGpa(19.87);
        education.setStartYear(2024);
        education.setEndYear(2026);
        return education;
    }

    public static Summary createSummary() {
        Summary summary = new Summary();
        summary.setText("""
                As a junior software developer with 2 years of hands-on experience, I thrive on turning complex challenges into elegant solutions. My proficiency in C# .NET and Java allows me to create robust applications that seamlessly bridge user needs with technical capabilities.
                Passionate about clean code and agile principles, I believe in the power of organized design and development to create software that stands the test of time. By adhering to best practices, I ensure that every line of code I write contributes to a maintainable and scalable product.
                In my journey, I've come to appreciate the collaborative nature of software development. I'm excited to work alongside like-minded professionals who share my dedication to crafting exceptional software. Let's connect and explore how we can drive innovation through code, design, and a commitment to continuous learning.""");
        return summary;
    }

    public static ContactMethod createContactMethod(Resume resume, ContactType type, String value) {
        ContactMethod contactMethod = new ContactMethod();
        contactMethod.setType(type);
        contactMethod.setContent(value);
        return contactMethod;
    }

    public static PersonalInformation createPersonalInformation() {
        PersonalInformation user = new PersonalInformation();
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setPhoneNumber("09017743009");
        user.setMaritalStatus(MaritalStatus.Single);
        user.setGender(Gender.Male);
        user.setMilitaryServiceStatus(MilitaryServiceStatus.Exempted);
        user.setBirthDate(LocalDate.of(1999, 7, 29));
        user.setForeigner(false);
        user.setDisabilityType(DisabilityType.None);
        return user;
    }

    public static Hobby createHobby(Resume resume, String title) {
        Hobby hobby = new Hobby();
        hobby.setTitle(title);
        return hobby;
    }

    public static Membership createMembership(Resume resume, String title, LocalDate date) {
        Membership membership = new Membership();
        membership.setTitle(title);
        membership.setDate(date);
        return membership;
    }

    public static Patent createPatent(Resume resume) {
        Patent patent = new Patent();
        patent.setTitle("Curtain Relay");
        patent.setDescription("A simple product that opens and blinds the curtains");
        patent.setRegistrationDate(LocalDate.of(2024, 3, 5));
        patent.setRegistrationNumber("A1B2C3");
        return patent;
    }

    public static Presentation createPresentation(Resume resume) {
        Presentation presentation = new Presentation();
        presentation.setTitle("Introduction of new product in company");
        presentation.setDate(LocalDate.of(2024, 5, 19));
        presentation.setReferenceLink(null);
        presentation.setDescription("In this event, I introduced my new product in the annual celebration of my company");
        return presentation;
    }

    public static Publication createPublication(Resume resume) {
        Publication publication = new Publication();
        publication.setTitle("Clean code book summary chapter by chapter");
        publication.setAuthor("Mohammadmahdi Farrokhy");
        publication.setPublisher("Mohammadmahdi Farrokhy");
        publication.setDate(LocalDate.of(2023, 9, 26));
        publication.setReferenceLink("https://github.com/mmahdi-farrokhy/CleanCodeBook");
        publication.setDescription("A summarized version of Clean Code book by Robert C. Martin in a simple language and practical code examples");
        return publication;
    }

    public static VolunteerActivity createVolunteerActivity(Resume resume, String title, int year) {
        VolunteerActivity volunteerActivity = new VolunteerActivity();
        volunteerActivity.setTitle(title);
        volunteerActivity.setYear(year);
        return volunteerActivity;
    }
}
