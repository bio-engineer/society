package ru.bioengineer.society.domain.gateway;

import org.springframework.security.core.Authentication;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

public interface AuthGateway {

    String login(String userId, String password) throws InvalidDataException, NotFoundException,
            InternalException, TemporaryException;

    Authentication getAuthentication(String token);

    boolean validateToken(String token);

}
