import csv
import logging
import random
import secrets
from datetime import datetime, timedelta

import bcrypt

_encoding = 'utf-8'


# Чтение данных из CSV файлов
def read_csv(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        return list(reader)


# Функция для генерации пароля
def generate_password(length):
    alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()"
    password = ''.join(secrets.choice(alphabet) for _ in range(length))
    return password


# Шифрование пароля с использованием BCrypt
def encrypt_password(password):
    password_bytes = password.encode(_encoding)
    hashed = bcrypt.hashpw(password_bytes, bcrypt.gensalt(rounds=4))
    return hashed.decode(_encoding)


# Генерация случайной даты рождения
def random_birth_date(start_year=1900, end_year=2022):
    start_date = datetime(start_year, 1, 1)
    end_date = datetime(end_year, 12, 31)

    time_between_dates = end_date - start_date
    random_number_of_days = random.randrange(time_between_dates.days)
    random_date = start_date + timedelta(days=random_number_of_days)

    return random_date.strftime('%Y-%m-%d')


# Чтение имен, фамилий и отчеств
names = read_csv('lookup/man_names.csv')
surnames = read_csv('lookup/man_last_names.csv')
patronymics = read_csv('lookup/man_middle_names.csv')
cities = read_csv('lookup/cities.csv')

# Настройка логгера
logging.basicConfig(level=logging.INFO)

# Создание нового CSV файла
with open('questionnaire.csv', 'w', newline='', encoding=_encoding) as f1, \
        open('passwords.csv', 'w', newline='', encoding=_encoding) as f2, \
        open('users.csv', 'w', newline='', encoding=_encoding) as f3:
    questionnaire_writer = csv.writer(f1)
    passwords_writer = csv.writer(f2)
    users_writer = csv.writer(f3)

    questionnaire_writer.writerow(['user_id', 'first_name', 'second_name', 'middle_name', 'birthdate', 'city'])
    passwords_writer.writerow(['user_id', 'password', 'encrypted_password'])
    users_writer.writerow(['password'])

    total_records = 10 ** 6
    # Генерация данных
    for i in range(total_records):  # Генерируем 1000000 строк
        name = random.choice(names)[0]
        surname = random.choice(surnames)[0]
        patronymic = random.choice(patronymics)[0]
        birth_date = random_birth_date()
        city = random.choice(cities)[0]
        password = generate_password(random.randrange(start=8, stop=25))
        encrypted_password = encrypt_password(password)

        user_number = i + 1
        questionnaire_writer.writerow([user_number, name, surname, patronymic, birth_date, city])
        passwords_writer.writerow([user_number, password, encrypted_password])
        users_writer.writerow([encrypted_password])

        # Логирование прогресса каждый процент
        if (user_number) % (total_records // 100) == 0:
            logging.info(f"Прогресс: {i / total_records * 100:.1f}%")
