package com.mmf.resumeBuilder.controller;

import com.mmf.resumeBuilder.entity.User;
import com.mmf.resumeBuilder.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid user info and failure of creating new user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Create user", description = "Creates and saves a new user in database")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update of user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid user info and failure of updating the user", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @Operation(summary = "Update user", description = "Updates an existing user in database")
    @PutMapping(value = "/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable String userEmail) {
        if (result.hasErrors()) {
            if (result.getErrorCount() == 1) {
                Optional<ObjectError> notDuplicatedEmail = result.getAllErrors()
                        .stream()
                        .filter(e -> Objects.equals(e.getCode(), "NotDuplicatedEmail"))
                        .findFirst();

                if (notDuplicatedEmail.isPresent()) {
                    return new ResponseEntity<>(userService.updateUser(user, userEmail), HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.updateUser(user, userEmail), HttpStatus.OK);
    }
}
