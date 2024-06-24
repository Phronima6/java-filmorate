package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Data
public class Film {

    @Size(max = 200, message = "Превышена длинна описания фильма.")
    String description; // Описания фильма
    @Positive(message = "Продолжительность фильма должна быть положительным числом.")
    int duration; // Продолжительность фильма
    int id; // Целочисленный идентификатор фильма
    @NotBlank(message = "Название фильма не может быть пустым.")
    String name; // Название фильма
    LocalDate releaseDate; // Дата релиза фильма

}