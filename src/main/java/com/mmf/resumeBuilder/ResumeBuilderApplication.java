package com.mmf.resumeBuilder;

import com.mmf.resumeBuilder.dao.ResumeDAO;
import com.mmf.resumeBuilder.entities.*;
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
import com.mmf.resumeBuilder.enums.user.detail.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ResumeBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeBuilderApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ResumeDAO resumeDAO) {
        return runner -> readResumeFromDatabase(resumeDAO);
    }

    private void readResumeFromDatabase(ResumeDAO resumeDAO) {
        int resumeId = 7;
        Resume resume = resumeDAO.findById(resumeId);
        User user = resumeDAO.findUser(resumeId);
        List<ContactMethod> contactInformation = resumeDAO.findContactInformation(resumeId);
        List<Education> educations = resumeDAO.findEducations(resumeId);
        List<TeachingAssistance> teachingAssistance = resumeDAO.findTeachingAssistance(resumeId);
        List<JobExperience> jobExperiences = resumeDAO.findJobExperiences(resumeId);
        List<FormerColleague> formerColleagues = resumeDAO.findFormerColleagues(resumeId);
        List<Research> researches = resumeDAO.findResearches(resumeId);
        List<Course> courses = resumeDAO.findCourses(resumeId);
        List<HardSkill> hardSkills = resumeDAO.findHardSkills(resumeId);
        List<SoftSkill> softSkills = resumeDAO.findSoftSkills(resumeId);
        List<Language> languages = resumeDAO.findLanguages(resumeId);
        List<Project> projects = resumeDAO.findProjects(resumeId);
        List<Patent> patents = resumeDAO.findPatents(resumeId);
        List<Presentation> presentations = resumeDAO.findPresentations(resumeId);
        List<Award> awards = resumeDAO.findAwards(resumeId);
        List<Publication> publications = resumeDAO.findPublications(resumeId);
        List<VolunteerActivity> volunteerActivities = resumeDAO.findVolunteerActivities(resumeId);
        List<Membership> memberships = resumeDAO.findMemberships(resumeId);
        List<Hobby> hobbies = resumeDAO.findHobbies(resumeId);

        if (user != null)
            System.out.println("user:" + user + "\n_________________________________________");
        else
            System.out.println("user is null\n_________________________________________");

        if (contactInformation != null)
            System.out.println(contactInformation + "\n_________________________________________");
        else
            System.out.println("contactInformation is null\n_________________________________________");

        if (educations != null)
            System.out.println(educations + "\n_________________________________________");
        else
            System.out.println("educations is null" + "\n_________________________________________");

        if (teachingAssistance != null)
            System.out.println("teachingAssistance:" + teachingAssistance + "\n_________________________________________");
        else
            System.out.println("teachingAssistance is null" + "\n_________________________________________");

        if (jobExperiences != null)
            System.out.println("jobExperiences:" + jobExperiences + "\n_________________________________________");
        else
            System.out.println("jobExperiences is null" + "\n_________________________________________");

        if (formerColleagues != null)
            System.out.println("formerColleagues:" + formerColleagues + "\n_________________________________________");
        else
            System.out.println("formerColleagues is null" + "\n_________________________________________");

        if (researches != null)
            System.out.println("researches:" + researches + "\n_________________________________________");
        else
            System.out.println("researches is null" + "\n_________________________________________");

        if (courses != null)
            System.out.println("courses:" + courses + "\n_________________________________________");
        else
            System.out.println("courses is null" + "\n_________________________________________");

        if (hardSkills != null)
            System.out.println("hardSkills:" + hardSkills + "\n_________________________________________");
        else
            System.out.println("hardSkills is null" + "\n_________________________________________");

        if (softSkills != null)
            System.out.println("softSkills:" + softSkills + "\n_________________________________________");
        else
            System.out.println("softSkills is null" + "\n_________________________________________");

        if (languages != null)
            System.out.println("languages:" + languages + "\n_________________________________________");
        else
            System.out.println("languages is null" + "\n_________________________________________");

        if (projects != null)
            System.out.println("projects:" + projects + "\n_________________________________________");
        else
            System.out.println("projects is null" + "\n_________________________________________");

        if (patents != null)
            System.out.println("patents:" + patents + "\n_________________________________________");
        else
            System.out.println("patents is null" + "\n_________________________________________");

        if (presentations != null)
            System.out.println("presentations:" + presentations + "\n_________________________________________");
        else
            System.out.println("presentations is null" + "\n_________________________________________");

        if (awards != null)
            System.out.println("awards:" + awards + "\n_________________________________________");
        else
            System.out.println("awards is null" + "\n_________________________________________");

        if (publications != null)
            System.out.println("publications:" + publications + "\n_________________________________________");
        else
            System.out.println("publications is null" + "\n_________________________________________");

        if (volunteerActivities != null)
            System.out.println("volunteerActivities:" + volunteerActivities + "\n_________________________________________");
        else
            System.out.println("volunteerActivities is null" + "\n_________________________________________");

        if (memberships != null)
            System.out.println("memberships:" + memberships + "\n_________________________________________");
        else
            System.out.println("memberships is null" + "\n_________________________________________");

        if (hobbies != null)
            System.out.println("hobbies:" + hobbies + "\n_________________________________________");
        else
            System.out.println("hobbies is null" + "\n_________________________________________");
    }

    private void addResume(ResumeDAO resumeDAO) {
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
        Language language1 = createLanguage(resume, LanguageName.German, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate, LanguageLevel.Pre_Intermediate);
        Language language2 = createLanguage(resume, LanguageName.English, LanguageLevel.Intermediate, LanguageLevel.Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate, LanguageLevel.Upper_Intermediate);
        Language language3 = createLanguage(resume, LanguageName.Persian, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native, LanguageLevel.Native);
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
        HardSkill hardSkill1 = createHardSkill(resume, HardSkillType.Spring_Boot, HardSkillLevel.Beginner);
        HardSkill hardSkill2 = createHardSkill(resume, HardSkillType.Java, HardSkillLevel.Intermediate);
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
        jobExperience.setCategory(JobCategory.Software_Development);
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
        jobExperience.setCategory(JobCategory.Software_Development);
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
        education.setDegreeLevel(DegreeLevel.Bachelor);
        education.setMajor(Major.Computer_Engineering);
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
        userDetail.setMaritalStatus(MaritalStatus.Single);
        userDetail.setGender(Gender.Male);
        userDetail.setMilitaryServiceStatus(MilitaryServiceStatus.Exempted);
        userDetail.setBirthDate(LocalDate.of(1999, 7, 29));
        userDetail.setForeigner(false);
        userDetail.setDisabilityType(DisabilityType.None);
        return userDetail;
    }
}
