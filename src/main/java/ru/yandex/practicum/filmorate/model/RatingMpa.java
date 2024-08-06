package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RatingMpa {

    int id; // Идентификатор рейтинга фильма
    @NotBlank(message = "Название рейтинга фильма не может быть пустым.")
    String name; // Название рейтинга фильма

}