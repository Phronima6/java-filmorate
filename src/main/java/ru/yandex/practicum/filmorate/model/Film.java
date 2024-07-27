package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Film {

    @Size(max = 200, message = "Превышена длинна описания фильма.")
    String description; // Описания фильма
    @Positive(message = "Продолжительность фильма должна быть положительным числом.")
    int duration; // Продолжительность фильма
    Set<Genre> genres = new HashSet<>(); // Жанры фильма
    int id; // Идентификатор фильма
    int idRatingMpa; // Идентификатор рейтинга фильма
    Set<Integer> likes = new HashSet<>(); // Оценки пользователей
    @NotBlank(message = "Название фильма не может быть пустым.")
    String name; // Название фильма
    RatingMpa mpa; // Рейтинга фильма
    LocalDate releaseDate; // Дата релиза фильма

}