package ru.bioengineer.society.infrastructure.gateway_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.UserGateway;
import ru.bioengineer.society.domain.model.User;
import ru.bioengineer.society.infrastructure.gateway_impl.dao.UserRepository;

import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserGatewayImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createNewUser(String password) throws InvalidDataException, InternalException, TemporaryException {
        String encodedPassword = passwordEncoder.encode(password);
        String userId = userRepository.saveNewUser(encodedPassword);
        return new User(userId, encodedPassword);
    }

    @Override
    public Optional<User> findByUserId(String userId) throws InternalException, TemporaryException {
        return userRepository.findById(userId);
    }

}
