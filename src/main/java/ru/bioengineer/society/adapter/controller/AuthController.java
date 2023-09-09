package ru.bioengineer.society.adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bioengineer.society.adapter.utils.WebUtils;
import ru.bioengineer.society.app.AuthService;
import ru.bioengineer.society.app.dto.AuthRequest;
import ru.bioengineer.society.app.dto.AuthResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

@RequestMapping("/")
@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse authResponse = authService.login(authRequest);
            return ResponseEntity.ok(authResponse);
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

}
