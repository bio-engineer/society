package ru.bioengineer.society.adapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bioengineer.society.app.UserService;
import ru.bioengineer.society.app.dto.QuestionnaireResponse;
import ru.bioengineer.society.app.dto.UserRegistrationRequest;
import ru.bioengineer.society.app.dto.UserRegistrationResponse;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserRegistrationResponse registration(@RequestBody UserRegistrationRequest request) {
        logger.info("User [firstName={} secondName={} birthdate={} city={}] try to register",
                request.firstName(), request.secondName(), request.birthdate(), request.city()
        );
        UserRegistrationResponse response = userService.registration(request);
        logger.info("User [{}] successful registered", response.userId());
        return response;
    }

    @GetMapping(value = "/get/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public QuestionnaireResponse get(@PathVariable("userId") String userId) {
        return userService.get(userId);
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Collection<QuestionnaireResponse> search(@RequestParam(value = "first_name", required = false) String firstName,
                                                    @RequestParam(value = "second_name", required = false) String secondName) {
        return userService.search(firstName, secondName);
    }

}
