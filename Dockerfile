# Используем OpenJDK 21 как базовый образ
FROM openjdk:21-jdk-slim AS builder

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы gradle wrapper и необходимые скрипты
COPY gradlew ./
COPY gradle ./gradle/

# Копируем файлы сборки (settings.gradle.kts и build.gradle.kts) в корень проекта
COPY settings.gradle.kts ./
COPY build.gradle.kts ./

# Копируем исходный код проекта
COPY src ./src

# Преобразуем файл gradlew, чтобы удалить символы возврата каретки (для Windows)
RUN sed -i -e 's/\r$//' gradlew

# Делаем gradlew исполняемым
RUN chmod +x ./gradlew

# Выполняем сборку с дополнительной отладочной информацией
RUN ./gradlew build -x test --stacktrace

# Используем снова OpenJDK 21 для финального образа
FROM openjdk:21-jdk-slim

# Задаем рабочую директорию
WORKDIR /app

# Копируем собранный артефакт из предыдущего этапа
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080

# Указываем, что будет запускаться при старте контейнера
ENTRYPOINT ["java", "-jar", "app.jar"]
