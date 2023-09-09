package ru.bioengineer.society.domain.gateway;

import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.model.Questionnaire;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface QuestionnaireGateway {

    void registration(String userId,
                      String firstName,
                      String secondName,
                      LocalDate birthdate,
                      String biography,
                      String city) throws InternalException, TemporaryException;


    Optional<Questionnaire> get(String userId) throws InternalException, TemporaryException;

    Collection<Questionnaire> search(String firstName, String secondName) throws InternalException, TemporaryException;

}
