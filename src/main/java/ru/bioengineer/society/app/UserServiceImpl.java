package ru.bioengineer.society.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bioengineer.society.app.dto.QuestionnaireResponse;
import ru.bioengineer.society.app.dto.UserRegistrationRequest;
import ru.bioengineer.society.app.dto.UserRegistrationResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.gateway.QuestionnaireGateway;
import ru.bioengineer.society.domain.gateway.UserGateway;
import ru.bioengineer.society.domain.model.Questionnaire;

import java.util.Collection;
import java.util.stream.Collectors;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Service
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;
    private final QuestionnaireGateway questionnaireGateway;

    @Autowired
    public UserServiceImpl(UserGateway userGateway, QuestionnaireGateway questionnaireGateway) {
        this.userGateway = userGateway;
        this.questionnaireGateway = questionnaireGateway;
    }

    @Override
    public UserRegistrationResponse registration(UserRegistrationRequest registrationRequest)
            throws InvalidDataException, InternalException, TemporaryException {
        if (isEmpty(registrationRequest.password())) {
            throw new InvalidDataException("Password can't be empty");
        }
        if (isEmpty(registrationRequest.firstName())) {
            throw new InvalidDataException("First name can't be empty");
        }

        String userId = userGateway.createNewUser(registrationRequest.password()).id();

        questionnaireGateway.registration(
                userId,
                registrationRequest.firstName(),
                registrationRequest.secondName(),
                registrationRequest.birthdate(),
                registrationRequest.biography(),
                registrationRequest.city()
        );

        return new UserRegistrationResponse(userId);
    }

    @Override
    public QuestionnaireResponse get(String userId)
            throws InvalidDataException, NotFoundException, InternalException, TemporaryException {
        if (isEmpty(userId)) {
            throw new InvalidDataException("user id can't be null");
        }
        return questionnaireGateway.get(userId)
                .map(UserServiceImpl::getResponse)
                .orElseThrow(() -> new NotFoundException("User with id [" + userId + "] not found"));
    }

    @Override
    public Collection<QuestionnaireResponse> search(String firstName, String secondName)
            throws InvalidDataException, InternalException, TemporaryException {
        if (isEmpty(firstName) && isEmpty(secondName)) {
            throw new InvalidDataException("You must specify a first or last name as a definition parameter");
        }
        return questionnaireGateway.search(firstName, secondName)
                .stream()
                .map(UserServiceImpl::getResponse)
                .collect(Collectors.toList());
    }

    private static QuestionnaireResponse getResponse(Questionnaire questionnaire) {
        return new QuestionnaireResponse(
                questionnaire.id(),
                questionnaire.firstName(),
                questionnaire.secondName(),
                questionnaire.age(),
                questionnaire.birthdate(),
                questionnaire.biography(),
                questionnaire.city()
        );
    }

}
