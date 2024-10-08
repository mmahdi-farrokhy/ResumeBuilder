package com.mmf.resumeBuilder.service;

import com.mmf.resumeBuilder.TempResumeGenerator;
import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillLevel;
import com.mmf.resumeBuilder.constants.hardskill.HardSkillType;
import com.mmf.resumeBuilder.entity.resume.HardSkill;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.exception.InvalidResumeException;
import com.mmf.resumeBuilder.exception.ResumeNotFoundException;
import com.mmf.resumeBuilder.exception.UserNotFoundException;
import com.mmf.resumeBuilder.repository.ResumeRepository;
import com.mmf.resumeBuilder.service.tools.word.WordProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.mmf.resumeBuilder.service.tools.word.WordProcessing.STORE_PATH;
import static com.mmf.resumeBuilder.service.tools.word.WordProcessing.generateFilePath;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ResumeServiceImplShould {

    @Mock
    ResumeRepository resumeJPARepository;

    @InjectMocks
    ResumeServiceImpl resumeService;

    Resume resume;

    @BeforeEach
    public void setUp() {
        resumeJPARepository = mock(ResumeRepository.class);
        resumeService = new ResumeServiceImpl(resumeJPARepository);
        resume = TempResumeGenerator.createResume();
        resume.setId(0);
    }

    @Test
    void find_resume_by_id() {
        when(resumeJPARepository.findById(1)).thenReturn(Optional.of(resume));
        assertThat(resumeService.findResumeById(1)).isEqualTo(resume);
    }

    @Test
    void find_resume_by_id_and_throw_exception_if_resume_id_is_invalid() {
        when(resumeJPARepository.findById(1)).thenReturn(empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.findResumeById(1))
                .withMessage("Resume with id 1 not found");
    }

    @Test
    void save_a_resume() {
        when(resumeJPARepository.save(resume)).thenReturn(resume);

        resumeService.saveResume(resume);

        ArgumentCaptor<Resume> resumeArgumentCaptor = ArgumentCaptor.forClass(Resume.class);
        verify(resumeJPARepository, times(1)).save(resumeArgumentCaptor.capture());

        Resume capturedResume = resumeArgumentCaptor.getValue();
        assertThat(capturedResume).isEqualTo(resume);
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
        when(resumeJPARepository.findById(resumeId)).thenReturn(empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.findResumeById(resumeId))
                .withMessage("Resume with id " + resumeId + " not found");
    }

    @Test
    void delete_a_resume_by_id_and_throw_exception_if_resume_id_is_invalid() {
        int resumeId = 1;
        when(resumeJPARepository.findById(resumeId)).thenReturn(empty());
        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.deleteResume(resumeId))
                .withMessage("Resume with id " + resumeId + " not found");
    }

    @Test
    void delete_a_resume() {
        when(resumeJPARepository.existsById(resume.getId())).thenReturn(true);
        resumeService.deleteResume(resume);
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
        verify(resumeJPARepository, times(1)).save(resumeArgumentCaptor.capture());
        Resume capturedResume = resumeArgumentCaptor.getValue();
        assertThat(capturedResume).isEqualTo(resume);

        verify(resumeJPARepository, times(1)).existsById(resume.getId());
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

        Resume returnedResume = resumeService.downloadResume(resumeId, ResumeTheme.ATSClassic);
        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeJPARepository, times(1)).findById(resumeId);
        assertTrue(fileExists(
                generateFilePath(resume.getPersonalInformation(), ResumeTheme.ATSClassic)));

        returnedResume = resumeService.downloadResume(resumeId, ResumeTheme.Classic_Accounting);
        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeJPARepository, times(2)).findById(resumeId);
        assertTrue(fileExists(
                generateFilePath(resume.getPersonalInformation(), ResumeTheme.Classic_Accounting)));

        returnedResume = resumeService.downloadResume(resumeId, ResumeTheme.Simple_Florist);
        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeJPARepository, times(3)).findById(resumeId);
        assertTrue(fileExists(
                generateFilePath(resume.getPersonalInformation(), ResumeTheme.Simple_Florist)));

        returnedResume = resumeService.downloadResume(resumeId, ResumeTheme.Woodworking);
        assertThat(returnedResume).isEqualTo(resume);
        verify(resumeJPARepository, times(4)).findById(resumeId);
        assertTrue(fileExists(
                generateFilePath(resume.getPersonalInformation(), ResumeTheme.Woodworking)));
    }

    private boolean fileExists(String fileNameWithLocation) {
        File dir = new File(WordProcessing.STORE_PATH);
        if (!dir.isDirectory()) {
            return false;
        }

        String fileName = fileNameWithLocation.replace(STORE_PATH, "");
        String fileNameWithoutExtensionAndSecond = fileName.substring(0, fileName.length() - 7);

        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().startsWith(fileNameWithoutExtensionAndSecond)) {
                return true;
            }
        }

        return false;
    }

    @Test
    void download_a_resume_and_throw_exception_if_resume_does_not_exist() {
        int resumeId = 1;
        when(resumeJPARepository.findById(resumeId)).thenReturn(empty());

        assertThatExceptionOfType(ResumeNotFoundException.class)
                .isThrownBy(() -> resumeService.downloadResume(resumeId, ResumeTheme.ATSClassic))
                .withMessage("Resume with id " + resumeId + " not found");
    }

    @Test
    void find_all_resumes_by_user_email() {
        String email = resume.getUser().getEmail();
        when(resumeJPARepository.findAllResumesByUserEmail(email))
                .thenReturn(singletonList(resume));

        List<Resume> allResumesByUserEmail = resumeService.findAllResumesByUserEmail(email);
        assertThat(allResumesByUserEmail).isEqualTo(singletonList(resume));
        verify(resumeJPARepository, times(1)).findAllResumesByUserEmail(email);
    }

    @Test
    void find_all_resumes_by_user_email_and_throw_exception_if_user_does_not_exist() {
        String email = resume.getUser().getEmail();
        when(resumeJPARepository.findAllResumesByUserEmail(email))
                .thenReturn(Collections.emptyList());

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> resumeService.findAllResumesByUserEmail(email))
                .withMessage("User with email " + email + " not found");
    }
}