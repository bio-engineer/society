import csv
import random
from datetime import datetime, timedelta
import secrets


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


# Генерация случайной даты рождения
def random_birth_date(start_year=1900, end_year=2022):
    start_date = datetime(start_year, 1, 1)
    end_date = datetime(end_year, 12, 31)

    time_between_dates = end_date - start_date
    random_number_of_days = random.randrange(time_between_dates.days)
    random_date = start_date + timedelta(days=random_number_of_days)

    return random_date.strftime('%d-%m-%Y')


# Чтение имен, фамилий и отчеств
names = read_csv('lookup/man_names.csv')
surnames = read_csv('lookup/man_last_names.csv')
patronymics = read_csv('lookup/man_middle_names.csv')
cities = read_csv('lookup/cities.csv')

# Создание нового CSV файла
with open('generated_mens.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(['last_name', 'name', 'middle_name', 'birthdate', 'password'])

    # Генерация данных
    for _ in range(1000000):  # Генерируем 1000000 строк
        name = random.choice(names)[0]
        surname = random.choice(surnames)[0]
        patronymic = random.choice(patronymics)[0]
        birth_date = random_birth_date()
        city = random.choice(cities)[0]
        password = generate_password(random.randrange(start=8, stop=25))

        writer.writerow([surname, name, patronymic, birth_date, city, password])
