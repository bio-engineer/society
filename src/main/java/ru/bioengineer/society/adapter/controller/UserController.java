package ru.bioengineer.society.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bioengineer.society.adapter.utils.WebUtils;
import ru.bioengineer.society.app.UserService;
import ru.bioengineer.society.app.dto.QuestionnaireResponse;
import ru.bioengineer.society.app.dto.UserRegistrationRequest;
import ru.bioengineer.society.app.dto.UserRegistrationResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> registration(@RequestBody UserRegistrationRequest registrationRequest) {
        try {
            UserRegistrationResponse registrationResponse = userService.registration(registrationRequest);
            return ResponseEntity.ok(registrationResponse);
        } catch (InvalidDataException e) {
            return WebUtils.createErrorResponse(e, 400);
        } catch (NotFoundException e) {
            return WebUtils.createErrorResponse(e, 404);
        } catch (InternalException e) {
            return WebUtils.createErrorResponse(e, 500);
        } catch (TemporaryException e) {
            return WebUtils.createErrorResponse(e, 503);
        }
    }

    @GetMapping(value = "/get/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> get(@PathVariable("userId") String userId) {
        try {
            QuestionnaireResponse questionnaire = userService.get(userId);
            return ResponseEntity.ok(questionnaire);
        } catch (InvalidDataException e) {
            return WebUtils.createErrorResponse(e, 400);
        } catch (NotFoundException e) {
            return WebUtils.createErrorResponse(e, 404);
        } catch (InternalException e) {
            return WebUtils.createErrorResponse(e, 500);
        } catch (TemporaryException e) {
            return WebUtils.createErrorResponse(e, 503);
        }
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> search(@RequestParam(value = "first_name", required = false) String firstName,
                                    @RequestParam(value = "second_name", required = false) String secondName) {
        try {
            Collection<QuestionnaireResponse> questionnaires = userService.search(firstName, secondName);
            return ResponseEntity.ok(questionnaires);
        } catch (InvalidDataException e) {
            return WebUtils.createErrorResponse(e, 400);
        } catch (InternalException e) {
            return WebUtils.createErrorResponse(e, 500);
        } catch (TemporaryException e) {
            return WebUtils.createErrorResponse(e, 503);
        }
    }

}
