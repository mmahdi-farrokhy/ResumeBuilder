package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.constants.ResumeTheme;
import com.mmf.resumeBuilder.entity.resume.Resume;
import com.mmf.resumeBuilder.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resume")
@AllArgsConstructor
@Tag(name = "Resume Controller")
public class ResumeController {
    private final ResumeService resumeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of resume", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Resume.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid resume info and failure of creating new user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Create resume", description = "Creates and saves a new resume in database")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        return new ResponseEntity<>(resumeService.saveResume(resume), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the resume", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class)))),
            @ApiResponse(responseCode = "500", description = "Resume does not exist in database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Delete user", description = "Deletes a resume from database")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Downloading was successfully operated", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Resume.class)))),
            @ApiResponse(responseCode = "500", description = "Resume does not exist in database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Download resume", description = "Get a resume from database and generate Word.docx file in project path in folder '/resumes'")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resume> downloadResume(@PathVariable int id, @RequestParam("theme") ResumeTheme theme) {
        return new ResponseEntity<>(resumeService.downloadResume(id, theme), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all resumes from database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Resume.class)))),
            @ApiResponse(responseCode = "500", description = "User with the specified email not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Find all resumes", description = "Gets all resumes of a user from database by its email")
    @GetMapping(value = "/all/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Resume>> findAllResumes(@PathVariable String email) {
        return new ResponseEntity<>(resumeService.findAllResumesByUserEmail(email), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Resume.class)))),
            @ApiResponse(responseCode = "500", description = "Resume with specified id does not exist in database", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Update resume", description = "Update an existing resume in database, like adding, removing or updating a resume section")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resume> updateResume(@RequestBody Resume resume, @PathVariable int id) {
        return new ResponseEntity<>(resumeService.updateResume(resume, id), HttpStatus.OK);
    }
}
