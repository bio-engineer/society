package ru.bioengineer.society.infrastructure.gateway_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.AuthGateway;
import ru.bioengineer.society.domain.model.User;
import ru.bioengineer.society.infrastructure.gateway_impl.dao.UserRepository;

@Component
public class AuthGatewayImpl implements AuthGateway {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthGatewayImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String userId, String password) throws InvalidDataException, NotFoundException, InternalException, TemporaryException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id [" + userId + "] not found"));

        if (!passwordEncoder.matches(password, user.passwordHash())) {
            throw new InvalidDataException("Wrong userId or password");
        }

        return "SOME_ACCESS_TOKEN";
    }

}
