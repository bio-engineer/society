package ru.bioengineer.society.domain.gateway;

import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

public interface AuthGateway {

    String login(String userId, String password) throws InvalidDataException, NotFoundException,
            InternalException, TemporaryException;

}
