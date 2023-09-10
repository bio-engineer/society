package ru.bioengineer.society.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bioengineer.society.app.dto.AuthRequest;
import ru.bioengineer.society.app.dto.AuthResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.AuthGateway;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthGateway authGateway;

    @Autowired
    public AuthServiceImpl(AuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) throws InvalidDataException, NotFoundException, InternalException, TemporaryException {
        if (authRequest == null) {
            throw new InvalidDataException("Request body can't be null");
        }
        if (authRequest.id() == null) {
            throw new InvalidDataException("Id can't be null");
        }
        if (authRequest.password() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        String token = authGateway.login(authRequest.id(), authRequest.password());
        return new AuthResponse(token);
    }

}
