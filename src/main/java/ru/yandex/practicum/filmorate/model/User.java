package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Data
public class User {

    @Past(message = "Некорректно введен день рождения пользователя.")
    LocalDate birthday; // Дата рождения
    @Email(message = "Некорректно введен email пользователя.")
    String email; // Электронная почта пользователя
    int id; // Целочисленный идентификатор пользователя
    @NotBlank(message = "Логин пользователя не может быть пустым.")
    String login; // Логин пользователя
    String name; // Имя пользователя для отображения

}