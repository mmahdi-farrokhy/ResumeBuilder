package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.CareerDAO;
import com.mmf.resumeBuilder.dao.EducationDAO;
import com.mmf.resumeBuilder.dao.ResumeDAO;
import com.mmf.resumeBuilder.dao.UserDAO;
import com.mmf.resumeBuilder.entities.*;
import com.mmf.resumeBuilder.enums.contactinformation.ContactType;
import com.mmf.resumeBuilder.enums.education.DegreeLevel;
import com.mmf.resumeBuilder.enums.education.Major;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.enums.hardskill.HardSkillType;
import com.mmf.resumeBuilder.enums.hardskill.SoftwareDevelopment;
import com.mmf.resumeBuilder.enums.job.JobCategory;
import com.mmf.resumeBuilder.enums.job.JobStatus;
import com.mmf.resumeBuilder.enums.language.LanguageLevel;
import com.mmf.resumeBuilder.enums.language.LanguageName;
import com.mmf.resumeBuilder.enums.location.City;
import com.mmf.resumeBuilder.enums.project.ProjectStatus;
import com.mmf.resumeBuilder.enums.user.detail.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserDAO userDAO, CareerDAO careerDAO, EducationDAO educationDAO, ResumeDAO resumeDAO) {
        return runner -> addUserCareerEducation(userDAO, careerDAO, educationDAO, resumeDAO);
    }

    private void addUserCareerEducation(UserDAO userDAO, CareerDAO careerDAO, EducationDAO educationDAO, ResumeDAO resumeDAO) {
        System.out.println("Creating resume");
        Resume resume = new Resume();
        resume.setUser(CreateUser());

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

    private void addProjects(Resume resume) {
        Project project1 = createProject(resume);
        resume.addProject(project1);
    }

    private void addLanguages(Resume resume) {
        Language language1 = createLanguage(resume, LanguageName.GERMAN, LanguageLevel.PRE_INTERMEDIATE, LanguageLevel.PRE_INTERMEDIATE, LanguageLevel.PRE_INTERMEDIATE, LanguageLevel.PRE_INTERMEDIATE, LanguageLevel.PRE_INTERMEDIATE);
        Language language2 = createLanguage(resume, LanguageName.ENGLISH, LanguageLevel.INTERMEDIATE, LanguageLevel.INTERMEDIATE, LanguageLevel.UPPER_INTERMEDIATE, LanguageLevel.UPPER_INTERMEDIATE, LanguageLevel.UPPER_INTERMEDIATE);
        Language language3 = createLanguage(resume, LanguageName.PERSIAN, LanguageLevel.NATIVE, LanguageLevel.NATIVE, LanguageLevel.NATIVE, LanguageLevel.NATIVE, LanguageLevel.NATIVE);
        resume.addLanguage(language1);
        resume.addLanguage(language2);
        resume.addLanguage(language3);
    }

    private void addSoftSkills(Resume resume) {
        SoftSkill softSkill1 = createSoftSkill(resume, "Teaching");
        SoftSkill softSkill2 = createSoftSkill(resume, "Presentation");
        resume.addSoftSkill(softSkill1);
        resume.addSoftSkill(softSkill2);
    }

    private void addHardSkills(Resume resume) {
        HardSkill hardSkill1 = createHardSkill(resume, HardSkillType.Spring_Boot, HardSkillLevel.BEGINNER);
        HardSkill hardSkill2 = createHardSkill(resume, HardSkillType.Java, HardSkillLevel.INTERMEDIATE);
        resume.addHardSkill(hardSkill1);
        resume.addHardSkill(hardSkill2);
    }

    private void addCourse(Resume resume) {
        Course course1 = createCourse(resume, "Java Expert", "7Learn", "https://7learn.com/crt?h=bdP9hiSTF4");
        Course course2 = createCourse(resume, "Spring Boot - Chad Darby", "Udemy", null);
        resume.addCourse(course1);
        resume.addCourse(course2);
    }

    private void addResearch(Resume resume) {
        Research research1 = createResearch(resume);
        resume.addResearch(research1);
    }

    private void addFormerColleague(Resume resume) {
        FormerColleague formerColleague1 = createFormerColleague1(resume);
        FormerColleague formerColleague2 = createFormerColleague2(resume);
        resume.addFormerColleague(formerColleague1);
        resume.addFormerColleague(formerColleague2);
    }

    private void addJobExperience(Resume resume) {
        JobExperience jobExperience1 = createJobExperience1(resume);
        JobExperience jobExperience2 = createJobExperience2(resume);
        resume.addJobExperience(jobExperience1);
        resume.addJobExperience(jobExperience2);
    }

    private void addTeachingAssistance(Resume resume) {
        TeachingAssistance teachingAssistance1 = createTeachingAssistance(resume);
        resume.addTeachingAssistance(teachingAssistance1);
    }

    private void addEducation(Resume resume) {
        Education education1 = CreateEducation(resume);
        resume.addEducation(education1);
    }

    private void addSummary(Resume resume) {
        Summary summary = CreateSummary();
        resume.setSummary(summary);
    }

    private void addContactMethods(Resume resume) {
        ContactMethod contactMethod1 = CreateContactMethod(resume, ContactType.Address, "Tehran, Pardis County");
        ContactMethod contactMethod2 = CreateContactMethod(resume, ContactType.Email, "mmahdifarrokhy@gmail.com");
        ContactMethod contactMethod3 = CreateContactMethod(resume, ContactType.LinkedIn, "https://www.linkedin.com/in/mmahdi-farrokhy/");
        resume.addContactMethod(contactMethod1);
        resume.addContactMethod(contactMethod2);
        resume.addContactMethod(contactMethod3);
    }

    private Project createProject(Resume resume) {
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

    private Language createLanguage(Resume resume, LanguageName name, LanguageLevel speaking, LanguageLevel writing, LanguageLevel listening, LanguageLevel reading, LanguageLevel research) {
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

    private SoftSkill createSoftSkill(Resume resume, String title) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setTitle(title);
        softSkill.setResume(resume);
        return softSkill;
    }

    private HardSkill createHardSkill(Resume resume, HardSkillType type, HardSkillLevel level) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setType(type);
        hardSkill.setLevel(level);
        hardSkill.setResume(resume);
        return hardSkill;
    }

    private Course createCourse(Resume resume, String name, String institute, String id) {
        Course course = new Course();
        course.setName(name);
        course.setInstitute(institute);
        course.setCredentialId(id);
        course.setResume(resume);
        return course;
    }

    private Research createResearch(Resume resume) {
        Research research = new Research();
        research.setTitle("Researching about Clean Code");
        research.setPublisher("Mohammad Mahdi Farrokhy");
        research.setReferenceLink("https://github.com/mmahdi-farrokhy/CleanCodeBook");
        research.setDate(LocalDate.of(2022, 7, 1));
        research.setDescription("In this repository you can read the simplified summary of Clean Code book by Robert C. Martin in separated chapters.");
        research.setResume(resume);
        return research;
    }

    private FormerColleague createFormerColleague2(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Mohammad Hossein Ommi");
        formerColleague.setPosition("CEO");
        formerColleague.setOrganizationName("Zarvan Stun Khodro");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    private FormerColleague createFormerColleague1(Resume resume) {
        FormerColleague formerColleague = new FormerColleague();
        formerColleague.setFullName("Ehsan Arbabi");
        formerColleague.setPosition("Manager");
        formerColleague.setOrganizationName("Negar Andishgan Co. Ltd.");
        formerColleague.setPhoneNumber("09123456789");
        formerColleague.setResume(resume);
        return formerColleague;
    }

    private JobExperience createJobExperience1(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("Software Developer And Consultant");
        jobExperience.setCategory(JobCategory.SOFTWARE_DEVELOPMENT);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Negar Andishgan Co. Ltd.");
        jobExperience.setDescription("Developing and refactoring NrSign.EMG medical test software");
        jobExperience.setStartDate(LocalDate.of(2023, 1, 7));
        jobExperience.setStatus(JobStatus.OCCUPIED);
        jobExperience.setLocation(createLocation());
        jobExperience.setResume(resume);
        return jobExperience;
    }

    private JobExperience createJobExperience2(Resume resume) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setTitle("C# Developer");
        jobExperience.setCategory(JobCategory.SOFTWARE_DEVELOPMENT);
        jobExperience.setSeniorityLevel(SeniorityLevel.Junior);
        jobExperience.setCompanyName("Zarvan Stun Khodro");
        jobExperience.setDescription("Developing automotive software Diag and Remap");
        jobExperience.setStartDate(LocalDate.of(2022, 4, 17));
        jobExperience.setEndDate(LocalDate.of(2022, 12, 21));
        jobExperience.setStatus(JobStatus.FINISHED);
        jobExperience.setLocation(createLocation());
        jobExperience.setResume(resume);
        return jobExperience;
    }

    private Location createLocation() {
        Location location = new Location();
        location.setId(1);
        location.setCityName(City.Tehran);
        location.setCountryId(location.getCityName().getCountryId());
        return location;
    }

    private TeachingAssistance createTeachingAssistance(Resume resume) {
        TeachingAssistance teachingAssistance = new TeachingAssistance();
        teachingAssistance.setTitle("Advanced Programming (Java)");
        teachingAssistance.setUniversity("Semnan University");
        teachingAssistance.setStartDate(LocalDate.of(2019, 1, 1));
        teachingAssistance.setEndDate(LocalDate.of(2019, 6, 30));
        teachingAssistance.setResume(resume);
        return teachingAssistance;
    }

    private Education CreateEducation(Resume resume) {
        Education education = new Education();
        education.setDegreeLevel(DegreeLevel.BACHELOR);
        education.setMajor(Major.COMPUTER_ENGINEERING);
        education.setUniversity("Semnan University");
        education.setGpa(16);
        education.setStartYear(2017);
        education.setEndYear(2021);
        education.setResume(resume);
        return education;
    }

    private Summary CreateSummary() {
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

    private ContactMethod CreateContactMethod(Resume resume, ContactType type, String value) {
        ContactMethod contactMethod = new ContactMethod();
        contactMethod.setType(type);
        contactMethod.setValue(value);
        contactMethod.setResume(resume);
        return contactMethod;
    }

    private User CreateUser() {
        User user = new User();
        user.setFirstName("Mohammad Mahdi");
        user.setLastName("Farrokhy");
        user.setPhoneNumber("09017743009");
        user.setUserDetail(CreateUserDetail());
        return user;
    }

    private UserDetail CreateUserDetail() {
        UserDetail userDetail = new UserDetail();
        userDetail.setMaritalStatus(MaritalStatus.SINGLE);
        userDetail.setGender(Gender.MALE);
        userDetail.setMilitaryServiceStatus(MilitaryServiceStatus.EXEMPTED);
        userDetail.setBirthDate(LocalDate.of(1999, 7, 29));
        userDetail.setForeigner(false);
        userDetail.setDisabilityType(DisabilityType.NONE);
        return userDetail;
    }
}
