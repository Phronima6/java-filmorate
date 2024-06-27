package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface FilmStorage {

    Map<Integer, Film> films = new HashMap<>();
    LocalDate FILM_BIRTHDAY = LocalDate.of(1895, 12, 28); // Дата рождения кино

    // Получение доступа к хранилищу с фильмами
    Map<Integer, Film> getFilms();

    // Получение всех фильмов
    Collection<Film> findAllFilms();

    // Добавление фильма
    Film createFilm(Film film);

    // Обновление фильма
    Film updateFilm(Film film);

    // Генератор целочисленного идентификатора фильма
    int getNextId();

}