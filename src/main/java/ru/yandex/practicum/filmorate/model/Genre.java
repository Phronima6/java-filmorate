package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Genre {

    int id; // Идентификатор жанра фильма
    @NotBlank(message = "Название жанра фильма не может быть пустыми.")
    String name; // Название жанра фильма

}