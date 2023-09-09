package ru.bioengineer.society.infrastructure.gateway_impl;

import org.springframework.stereotype.Component;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.QuestionnaireGateway;
import ru.bioengineer.society.domain.model.Questionnaire;
import ru.bioengineer.society.infrastructure.gateway_impl.dao.QuestionnaireRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Component
public class QuestionnaireGatewayImpl implements QuestionnaireGateway {

    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireGatewayImpl(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @Override
    public void registration(String userId,
                             String firstName,
                             String secondName,
                             LocalDate birthdate,
                             String biography,
                             String city) throws InternalException, TemporaryException {
        questionnaireRepository.save(userId, firstName, secondName, birthdate, biography, city);
    }

    @Override
    public Optional<Questionnaire> get(String userId) throws InternalException, TemporaryException {
        return questionnaireRepository.get(userId);
    }

    @Override
    public Collection<Questionnaire> search(String firstName, String secondName) throws InternalException, TemporaryException {
        return questionnaireRepository.search(firstName, secondName);
    }

}
