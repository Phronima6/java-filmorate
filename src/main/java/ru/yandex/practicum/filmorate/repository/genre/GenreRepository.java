package ru.yandex.practicum.filmorate.repository.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository {

    // Добавляем жанры фильма в базу данных
    void addFilmGenre(int idFilm, Set<Genre> genres);

    // Удаляем жанры фильма из базы данных
    void deleteFilmGenres(int idFilm);

    // Получение всех жанров фильмов
    Collection<Genre> findAllGenres();

    // Получение всех жанров определённого фильма
    Set<Genre> findGenres(int idFilm);

    // Получение жанра
    Optional<Genre> findGenre(int idGenre);

}