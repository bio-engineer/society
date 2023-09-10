package ru.bioengineer.society.infrastructure.gateway_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.AuthGateway;
import ru.bioengineer.society.infrastructure.config.JwtTokenProvider;
import ru.bioengineer.society.infrastructure.gateway_impl.dao.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthGatewayImpl implements AuthGateway {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private Map<String, Authentication> authenticatedUsers;

    @Autowired
    public AuthGatewayImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticatedUsers = new HashMap<>();
    }

    @Override
    public String login(String userId, String password) throws InvalidDataException, NotFoundException, InternalException, TemporaryException {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id [" + userId + "] not found"));

        UsernamePasswordAuthenticationToken passwordAuth = new UsernamePasswordAuthenticationToken(userId, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(passwordAuth);
            this.authenticatedUsers.put(userId, authenticate);
        } catch (AuthenticationException e) {
            throw new InvalidDataException("Wrong userId or password");
        }

        return jwtTokenProvider.createToken(userId);
    }

    @Override
    public Authentication getAuthentication(String token) {
        String username = jwtTokenProvider.getUsername(token);
        return authenticatedUsers.get(username);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

}
