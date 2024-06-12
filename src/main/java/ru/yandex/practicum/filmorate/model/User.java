package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class User {

    @Past(message = "Некорректно введен день рождения пользователя.")
    private LocalDate birthday; // Дата рождения
    @Email(message = "Некорректно введен email пользователя.")
    private String email; // Электронная почта пользователя
    private int id; // Целочисленный идентификатор пользователя
    @NotBlank(message = "Логин пользователя не может содержать только пробелы.")
    @NotNull(message = "Логин пользователя не может быть пустым.")
    private String login; // Логин пользователя
    private String name; // Имя пользователя для отображения

}