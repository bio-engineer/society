import csv
import requests

_encoding = 'utf-8'


# Открываем файл с данными о пользователях
with open('questionnaire.csv', 'r', encoding=_encoding) as f1, \
        open('passwords.csv', 'r', encoding=_encoding) as f2:
    questionnaire_reader = csv.DictReader(f1)
    passwords_reader = csv.DictReader(f2)

    questionnaire_data = list(questionnaire_reader)
    passwords_data = list(passwords_reader)

    quest_array = []
    for i in range(len(questionnaire_data)):
        questionnaire = questionnaire_data[i]
        password = passwords_data[i]

        quest_array.append(
            {
                "firstName": questionnaire['first_name'],
                "secondName": questionnaire['second_name'],
                "birthdate": questionnaire['birthdate'],
                "city": questionnaire['city'],
                "password": password['password']
            }
        )
        print(f'Handle {i} questionnaire')

    # Отправляем POST-запросы на регистрацию пользователей
    for item in quest_array:

        response = requests.post('http://localhost:8080/user/register', json=item)

        # Проверка статуса ответа
        if response.status_code == 200:
            print(f'Пользователь {item["secondName"]} {item["firstName"]} успешно зарегистрирован.')
        else:
            print(f'Ошибка регистрации пользователя {item["secondName"]} {item["firstName"]}. Статус ответа: {response.status_code}. Сообщение: {response.text}.')
