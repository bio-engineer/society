package ru.bioengineer.society.app;

import ru.bioengineer.society.app.dto.QuestionnaireResponse;
import ru.bioengineer.society.app.dto.UserRegistrationRequest;
import ru.bioengineer.society.app.dto.UserRegistrationResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.NotFoundException;
import ru.bioengineer.society.domain.exception.TemporaryException;

import java.util.Collection;

public interface UserService {

    /**
     * Регистрирует нового пользователя и возвращает его уникальный ID
     *
     * @param registrationRequest Запрос на регистрацию нового пользователя
     * @return Идентификатор пользователя
     * @throws InvalidDataException Возвращается в случае если переданы невалидные данные
     * @throws InternalException    Внутренняя ошибка сервера
     * @throws TemporaryException   Временная ошибка сервера. Необходимо повторить запрос позже
     */
    UserRegistrationResponse registration(UserRegistrationRequest registrationRequest) throws InvalidDataException,
            InternalException, TemporaryException;

    /**
     * Возвращает анкету пользователя
     *
     * @param userId ИД пользователя
     * @return Анкета пользователя
     * @throws InvalidDataException Невалидные данные
     * @throws NotFoundException    Анекета не найдена
     * @throws InternalException    Внутренняя ошибка сервера
     * @throws TemporaryException   Временная ошибка сервера. Необходимо повторить запрос позже
     */
    QuestionnaireResponse get(String userId) throws InvalidDataException, NotFoundException,
            InternalException, TemporaryException;


    /**
     * Возвращает список анкет отфильтрованных по условию
     *
     * @param firstName  Условие поиска по имени
     * @param secondName Условие поиска по фамилии
     * @return Список анкет
     * @throws InvalidDataException Невалидные данные
     * @throws InternalException    Внутренняя ошибка сервера
     * @throws TemporaryException   Временная ошибка сервера. Необходимо повторить запрос позже
     */
    Collection<QuestionnaireResponse> search(String firstName, String secondName) throws InvalidDataException, InternalException,
            TemporaryException;

}
