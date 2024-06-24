package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    Map<Integer, Film> films = new HashMap<>();
    static LocalDate FILM_BIRTHDAY = LocalDate.of(1895, 12, 28); // Дата рождения кино

    @GetMapping // Получение всех фильмов
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping // Добавление фильма
    public Film create(@Valid @RequestBody Film film) {
        if (!film.getReleaseDate().isBefore(FILM_BIRTHDAY)) {
            film.setId(getNextId());
            films.put(film.getId(), film);
        } else {
            log.error("Дата релиза фильма не может быть раньше 28 декабря 1895 года.");
            throw new ValidationException();
        }
        log.info("Фильм добавлен.");
        return film;
    }

    @PutMapping // Обновление фильма
    public Film update(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.error("Фильм с id = {} не найден.",film.getId());
            throw new NotFoundException();
        }
        log.info("Фильм обновлён.");
        return film;
    }

    // Генератор целочисленного идентификатора фильма
    private int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}