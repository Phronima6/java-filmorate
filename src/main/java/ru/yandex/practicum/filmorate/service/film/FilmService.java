package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;

public interface FilmService {

    LocalDate FILM_BIRTHDAY = LocalDate.of(1895, 12, 28); // Дата рождения кино

    // Проверка на наличие фильма в базе данных
    void checkFilm(int idFilm, String massageLog, String massageException);

    // Проверка на дату рождения фильма
    void checkFilmBirthday(Film film);

    // Проверка на наличие хотя бы одного фильма в базе данных
    //void checkIsEmpty();

    // Проверка на наличие пользователя в базе данных
    void checkUser(int idUser, String massageLog, String massageException);

    // Проверка на правильность переданных рейтинга и жанров
    void checkRatingMpaGenres(Film film);

    // Добавление фильма
    Film createFilm(Film film);

    // Добавление лайка фильму, выбранному пользователем
    Film createLikeOfFilm(int idFilm, int idUser);

    // Удаление лайка пользователя, поставленного фильму
    Film deleteLikeOfFilm(int idFilm, int idUser);

    // Получение всех фильмов
    Collection<Film> findAllFilms();

    // Получение фильма
    Film findFilm(int idFilm);

    // Получение списка из первых count фильмов, отсортированных по количеству лайков
    Collection<Film> findPopularFilms(int count);

    // Обновление фильма
    Film updateFilm(Film film);

}