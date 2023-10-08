import asyncio
import csv

import aiohttp

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


# Функция для выполнения POST-запроса
async def send_post(sem, session, item):
    async with sem, session.post('http://localhost:8080/user/register', json=item) as response:
        print(f'Status code: {response.status()}')


# Создаем сессию и запускаем POST-запросы
async def main():
    sem = asyncio.Semaphore(1000)  # Ограничиваем количество одновременных запросов
    async with aiohttp.ClientSession() as session:
        count = 0
        tasks = []
        for item in json_data:
            task = asyncio.create_task(send_post(sem, session, item))
            tasks.append(task)
            count += 1
            print(f'Добавлено в очередь: {count} задач')
        await asyncio.gather(*tasks)


# Запускаем цикл событий
asyncio.run(main())
