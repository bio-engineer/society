package ru.bioengineer.society.infrastructure.gateway_impl.dao;

import ru.bioengineer.society.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    String saveNewUser(String password);

    Optional<User> findById(String userId);

}
