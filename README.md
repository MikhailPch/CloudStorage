# Приложение "Облачное хранилище"
___
- Приложение разработано с использованием Spring Boot
- Использован сборщик пакетов Maven
- Для запуска используется Docker, Docker-Compose
## Описание
___
Проект предоставляет собой REST сервис для загрузки, скачивания, переименования, удаления и вывода списка загруженных файлов пользователя.
Все запросы к сервису авторизованы.
Веб приложение (FRONT) подключается к сервису и использует его функционал для манипуляции с файлами.
Информация о файлах и пользователях, хранится в БД. Для работы с БД используется СУБД PostgreSQL.

## Запуск FRONT
___
Установите nodejs (версия не ниже 19.7.0) на компьютер, следуя инструкции.
Скачайте [FRONT](https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-frontend) (JavaScript).
Перейдите в папку FRONT приложения и все команды для запуска выполняйте из неё.
Запустите nodejs-приложение (`npm install`, `npm run serve`).
Далее, в файле `.env` FRONT (находится в корне проекта) приложения нужно изменить url до backend: `VUE_APP_BASE_URL=http://localhost:8090`.
Запустите FRONT снова: `npm run serve`.
Откройте в браузере страницу по адресу http://localhost:8080/
## Запуск REST приложения
___
Для запуска приложения используются команды Docker

1. Скачать или склонировать репозиторий приложения в программу IntelliJ IDEA
2. Упаковать проект в Maven
3. В терминале приложения командой `docker build` создать образ приложения
4. Запустить контейнер командой `docker-compose up`
Для остановки приложения нажать Ctrl+C.
В приложении можно авторизоваться используя данные: имя пользователя - **ivanov**, пароль **password1**
имя пользователя - **petrov**, пароль **password2**.