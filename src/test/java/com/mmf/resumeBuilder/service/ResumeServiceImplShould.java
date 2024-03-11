package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.DatabaseTest;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillType;
import com.mmf.resumeBuilder.entity.resume.HardSkill;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.exception.InvalidResumeException;
import com.mmf.resumeBuilder.exception.ResumeNotFoundException;
import com.mmf.resumeBuilder.repository.ResumeJPARepository;
import com.mmf.resumeBuilder.repository.ResumeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ResumeServiceImplShould {

    @Mock
    ResumeRepositoryImpl resumeRepository;

    @Mock
    ResumeJPARepository resumeJPARepository;

    @InjectMocks
    ResumeServiceImpl resumeService;

    Resume resume;

    @BeforeEach
    public void setUp() throws Exception {
        resumeJPARepository = mock(ResumeJPARepository.class);
        resumeRepository = mock(ResumeRepositoryImpl.class);
        resumeService = new ResumeServiceImpl(resumeRepository, resumeJPARepository);
        resume = DatabaseTest.createResume();
        resume.setId(0);
    }

    @Test
    void find_resume_by_id() {
        when(resumeJPARepository.findById(1)).thenReturn(Optional.of(resume));
        assertThat(resumeService.findResumeById(1)).isEqualTo(resume);
    }

    @Test
    void find_resume_by_id_and_throw_exception_if_resume_id_is_invalid() {
        when(resumeJPARepository.findById(1)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.findResumeById(1))
                .withMessage("Resume with id 1 not found");
    }

    @Test
    void save_a_resume() {
        when(resumeJPARepository.save(resume)).thenReturn(resume);

        resumeService.saveResume(resume);

        ArgumentCaptor<Resume> resumeArgumentCaptor = ArgumentCaptor.forClass(Resume.class);
        verify(resumeJPARepository).save(resumeArgumentCaptor.capture());
        Resume capturedResume = resumeArgumentCaptor.getValue();
        assertThat(capturedResume.getPersonalInformation()).isEqualTo(resume.getPersonalInformation());
        assertThat(capturedResume.getContactInformation()).isEqualTo(resume.getContactInformation());
        assertThat(capturedResume.getSummary()).isEqualTo(resume.getSummary());
        assertThat(capturedResume.getEducations()).isEqualTo(resume.getEducations());
        assertThat(capturedResume.getTeachingAssistance()).isEqualTo(resume.getTeachingAssistance());
        assertThat(capturedResume.getJobExperiences()).isEqualTo(resume.getJobExperiences());
        assertThat(capturedResume.getFormerColleagues()).isEqualTo(resume.getFormerColleagues());
        assertThat(capturedResume.getResearches()).isEqualTo(resume.getResearches());
        assertThat(capturedResume.getCourses()).isEqualTo(resume.getCourses());
        assertThat(capturedResume.getHardSkills()).isEqualTo(resume.getHardSkills());
        assertThat(capturedResume.getSoftSkills()).isEqualTo(resume.getSoftSkills());
        assertThat(capturedResume.getLanguages()).isEqualTo(resume.getLanguages());
        assertThat(capturedResume.getProjects()).isEqualTo(resume.getProjects());
        assertThat(capturedResume.getPatents()).isEqualTo(resume.getPatents());
        assertThat(capturedResume.getPresentations()).isEqualTo(resume.getPresentations());
        assertThat(capturedResume.getAwards()).isEqualTo(resume.getAwards());
        assertThat(capturedResume.getPublications()).isEqualTo(resume.getPublications());
        assertThat(capturedResume.getVolunteerActivities()).isEqualTo(resume.getVolunteerActivities());
        assertThat(capturedResume.getMemberships()).isEqualTo(resume.getMemberships());
        assertThat(capturedResume.getHobbies()).isEqualTo(resume.getHobbies());
        assertThat(capturedResume.getUser()).isEqualTo(resume.getUser());
    }

    @Test
    void save_a_resume_and_throw_exception_if_user_is_null() {
        when(resumeJPARepository.save(resume)).thenReturn(resume);
        resume.setUser(null);
        assertThatExceptionOfType(InvalidResumeException.class)
                .isThrownBy(() -> resumeService.saveResume(resume))
                .withMessage("Resume must have a non-null user");
    }

    @Test
    void delete_a_resume_by_id() {
        Integer resumeId = 1;
        when(resumeJPARepository.findById(resumeId)).thenReturn(Optional.of(resume));
        assertThat(resumeService.findResumeById(resumeId)).isEqualTo(resume);

        resumeService.deleteResume(resumeId);
        when(resumeJPARepository.findById(resumeId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.findResumeById(resumeId))
                .withMessage("Resume with id " + resumeId + " not found");
    }

    @Test
    void delete_a_resume_by_id_and_throw_exception_if_resume_id_is_invalid() {
        int resumeId = 1;
        when(resumeJPARepository.findById(resumeId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.findResumeById(resumeId))
                .withMessage("Resume with id " + resumeId + " not found");
    }

    @Test
    void delete_a_resume() {
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(true);
        resumeService.deleteResume(resume);
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(false);
    }

    @Test
    void delete_a_resume_and_throw_exception_if_resume_does_not_exist() {
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(false);
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.deleteResume(resume))
                .withMessage("Resume with id " + resume.getId() + " not found");
    }

    @Test
    void update_a_resume() {
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(true);
        when(resumeJPARepository.save(resume)).thenReturn(resume);
        resume.addSection(new HardSkill(HardSkillType.Angular, HardSkillLevel.Beginner, resume));

        resumeService.updateResume(resume, resume.getId());

        ArgumentCaptor<Resume> resumeArgumentCaptor = ArgumentCaptor.forClass(Resume.class);
        verify(resumeJPARepository).save(resumeArgumentCaptor.capture());
        Resume capturedResume = resumeArgumentCaptor.getValue();
        assertThat(capturedResume.getPersonalInformation()).isEqualTo(resume.getPersonalInformation());
        assertThat(capturedResume.getContactInformation()).isEqualTo(resume.getContactInformation());
        assertThat(capturedResume.getSummary()).isEqualTo(resume.getSummary());
        assertThat(capturedResume.getEducations()).isEqualTo(resume.getEducations());
        assertThat(capturedResume.getTeachingAssistance()).isEqualTo(resume.getTeachingAssistance());
        assertThat(capturedResume.getJobExperiences()).isEqualTo(resume.getJobExperiences());
        assertThat(capturedResume.getFormerColleagues()).isEqualTo(resume.getFormerColleagues());
        assertThat(capturedResume.getResearches()).isEqualTo(resume.getResearches());
        assertThat(capturedResume.getCourses()).isEqualTo(resume.getCourses());
        assertThat(capturedResume.getHardSkills()).isEqualTo(resume.getHardSkills());
        assertThat(capturedResume.getSoftSkills()).isEqualTo(resume.getSoftSkills());
        assertThat(capturedResume.getLanguages()).isEqualTo(resume.getLanguages());
        assertThat(capturedResume.getProjects()).isEqualTo(resume.getProjects());
        assertThat(capturedResume.getPatents()).isEqualTo(resume.getPatents());
        assertThat(capturedResume.getPresentations()).isEqualTo(resume.getPresentations());
        assertThat(capturedResume.getAwards()).isEqualTo(resume.getAwards());
        assertThat(capturedResume.getPublications()).isEqualTo(resume.getPublications());
        assertThat(capturedResume.getVolunteerActivities()).isEqualTo(resume.getVolunteerActivities());
        assertThat(capturedResume.getMemberships()).isEqualTo(resume.getMemberships());
        assertThat(capturedResume.getHobbies()).isEqualTo(resume.getHobbies());
        assertThat(capturedResume.getUser()).isEqualTo(resume.getUser());
    }

    @Test
    void update_a_resume_and_throw_exception_if_resume_does_not_exist() {
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(false);
        when(resumeJPARepository.save(resume)).thenReturn(resume);
        resume.addSection(new HardSkill(HardSkillType.Angular, HardSkillLevel.Beginner, resume));

        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.updateResume(resume, resume.getId()))
                .withMessage("Resume with id " + resume.getId() + " not found");
    }

    @Test
    void download_a_resume() {
        int resumeId = 1;
        when(resumeJPARepository.findById(resumeId)).thenReturn(Optional.of(resume));
//        assertThat(resumeService.findResumeById(resumeId)).isEqualTo(resume);
        resumeService.downloadResume(resumeId);
    }

    @Test
    void findAllResumesByUserEmail() {
    }
}