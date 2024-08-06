package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import java.util.Collection;

public interface GenreService {

    // Получение всех жанров фильмов
    Collection<Genre> findAllGenres();

    // Получение всех жанров определённого фильма
    Collection<Genre> findGenres(int idFilm);

    // Получение жанра
    Genre findGenre(int idGenre);

}