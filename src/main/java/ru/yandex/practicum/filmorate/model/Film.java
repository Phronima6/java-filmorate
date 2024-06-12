package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Film {

    @Size(max = 200, message = "Превышена длинна описания фильма.")
    private String description; // Описания фильма
    @Positive(message = "Продолжительность фильма должна быть положительным числом.")
    private int duration; // Продолжительность фильма
    private int id; // Целочисленный идентификатор фильма
    @NotBlank(message = "Название фильма не может содержать только пробелы.")
    @NotNull(message = "Название фильма не может быть пустым.")
    private String name; // Название фильма
    private LocalDate releaseDate; // Дата релиза фильма

}