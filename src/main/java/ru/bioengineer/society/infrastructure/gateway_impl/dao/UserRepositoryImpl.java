package ru.bioengineer.society.infrastructure.gateway_impl.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final DataSource dataSource;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String saveNewUser(String password) {
        String insert = "INSERT INTO usr.users(password) VALUES (?) RETURNING id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert);) {

            ps.setString(1, password);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (!resultSet.next()) {
                    throw new InternalException("Error while trying to save new user");
                }
                return String.valueOf(resultSet.getInt("id"));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new InternalException("Error while trying to save new user");
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        String insert = "select id, password from usr.users where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert);) {

            ps.setInt(1, Integer.parseInt(userId));

            try (ResultSet resultSet = ps.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                User user = new User(
                        String.valueOf(resultSet.getInt("id")),
                        resultSet.getString("password")
                );
                return Optional.of(user);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new InternalException("User with id [" + userId + "] not found");
        }
    }


}
