import csv
import requests

# Словарь для преобразования имен полей
fields_map = {
    'last_name': 'secondName',
    'name': 'firstName',
    'middle_name': 'middleName',
    'birthdate': 'birthdate',
    'city': 'city',
    'password': 'password'
}

# Открываем файл с данными о пользователях
with open('generated_mens.csv', 'r') as file:
    csv_reader = csv.DictReader(file)
    data = list(csv_reader)

    # Преобразуем данные в JSON-объекты
    json_data = [
        {fields_map[key]: value for key, value in row.items()} for row in data
    ]

    # Отправляем POST-запросы на регистрацию пользователей
    for item in json_data:

        response = requests.post('http://localhost:8080/user/register', json=item)

        # Проверка статуса ответа
        if response.status_code == 200:
            print(f'Пользователь {item["secondName"]} {item["firstName"]} успешно зарегистрирован.')
        else:
            print(f'Ошибка регистрации пользователя {item["secondName"]} {item["firstName"]}. Статус ответа: {response.status_code}. Сообщение: {response.text}.')
