# Используем официальный образ PostgreSQL
FROM postgres:latest

# Устанавливаем переменные окружения для настройки базы данных
ENV POSTGRES_DB='postgres'
ENV POSTGRES_USER='postgres'
ENV POSTGRES_PASSWORD='Kolos213'

# Копируем SQL-скрипты в контейнер (если есть)
COPY ./init.sql /docker-entrypoint-initdb.d/

# Открываем порт для подключения к базе данных
EXPOSE 5432
