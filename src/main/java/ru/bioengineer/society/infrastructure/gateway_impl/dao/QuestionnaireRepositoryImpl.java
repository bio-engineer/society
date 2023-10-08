package ru.bioengineer.society.infrastructure.gateway_impl.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.model.Questionnaire;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class QuestionnaireRepositoryImpl implements QuestionnaireRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireRepositoryImpl.class);

    private final DataSource dataSource;

    public QuestionnaireRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(String userId,
                     String firstName,
                     String secondName,
                     LocalDate birthdate,
                     String biography,
                     String city) throws InternalException, TemporaryException {

        String insert = """
                INSERT INTO usr.questionnaire(user_id, first_name, second_name, birthdate, biography, city) VALUES
                (?, ?, ?, ?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert);) {

            ps.setInt(1, Integer.parseInt(userId));
            ps.setString(2, firstName);
            ps.setString(3, secondName);
            ps.setObject(4, birthdate);
            ps.setString(5, biography);
            ps.setString(6, city);

            ps.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new InternalException("Error while trying to user registration");
        }
    }

    @Override
    public Optional<Questionnaire> get(String userId) throws InternalException, TemporaryException {
        String select = "SELECT user_id, first_name, second_name, birthdate, biography, city FROM usr.questionnaire q WHERE q.user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(select);) {
            ps.setInt(1, Integer.parseInt(userId));

            try (ResultSet rs = ps.executeQuery();) {

                if (!rs.next()) {
                    return Optional.empty();
                }

                Questionnaire questionnaire = getQuestionnaire(rs);
                return Optional.of(questionnaire);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new InternalException("Error while trying to get user");
        }


    }

    @Override
    public Collection<Questionnaire> search(String firstName, String secondName) throws InternalException, TemporaryException {
        String insert = """
                SELECT user_id, first_name, second_name, birthdate, biography, city FROM usr.questionnaire
                WHERE (? IS NULL OR first_name like ?)
                    AND (? IS NULL OR second_name like ?);
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(insert);) {
            ps.setString(1, firstName);
            ps.setString(2, "%" + firstName + "%");

            ps.setString(3, secondName);
            ps.setString(4, "%" + secondName + "%");

            Collection<Questionnaire> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    result.add(getQuestionnaire(rs));
                }
            }
            return result;

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new InternalException("Error while trying to search user");
        }

    }

    private static Questionnaire getQuestionnaire(ResultSet rs) throws SQLException {
        return new Questionnaire(
                rs.getString("user_id"),
                rs.getString("first_name"),
                rs.getString("second_name"),
                Optional.ofNullable(rs.getDate("birthdate")).map(Date::toLocalDate).orElse(null),
                rs.getString("biography"),
                rs.getString("city")
        );
    }

}
