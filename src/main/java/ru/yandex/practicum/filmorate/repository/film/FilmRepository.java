package ru.yandex.practicum.filmorate.repository.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.Set;

public interface FilmRepository {

    // Добавление фильма
    Film createFilm(Film film);

    // Добавление лайка пользователя, выбранному фильму
    Film createLikeOfFilm(int idFilm, int idUser);

    // Удаление лайка пользователя, поставленного фильму
    Film deleteLikeOfFilm(int idFilm, int idUser);

    // Получение всех фильмов
    Collection<Film> findAllFilms();

    // Получение фильма
    Film findFilm(int idFilm);

    // Получение всех лайков, поставленных фильму
    Set<Integer> findLikes(int idFilm);

    // Обновление фильма
    Film updateFilm(Film film);

}