package ru.bioengineer.society.app;

import ru.bioengineer.society.app.dto.AuthRequest;
import ru.bioengineer.society.app.dto.AuthResponse;
import ru.bioengineer.society.domain.exception.InternalException;
import ru.bioengineer.society.domain.exception.InvalidDataException;
import ru.bioengineer.society.domain.exception.TemporaryException;
import ru.bioengineer.society.domain.exception.NotFoundException;

public interface AuthService {

    /**
     * Выполняет аутентификацию и возвращает токен для авторизации
     *
     * @param authRequest - запрос на аутентификацию
     * @return - токен для авторизации
     * @throws InvalidDataException - Невалидные данные
     * @throws NotFoundException    - Пользователь не найден
     * @throws InternalException    - Внутренняя ошибка сервера
     * @throws TemporaryException   - Временная ошибка сервера. Необходимо повторить запрос позже
     */
    AuthResponse login(AuthRequest authRequest) throws InvalidDataException, NotFoundException,
            InternalException, TemporaryException;

}
