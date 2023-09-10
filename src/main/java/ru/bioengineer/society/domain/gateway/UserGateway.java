package ru.bioengineer.society.domain.gateway;

import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.model.User;

import java.util.Optional;

public interface UserGateway {

    User createNewUser(String password) throws InvalidDataException, InternalException, TemporaryException;

    Optional<User> findByUserId(String userId) throws InternalException, TemporaryException;

}
