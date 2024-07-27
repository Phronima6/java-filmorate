package ru.yandex.practicum.filmorate.service.film;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.DataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.film.FilmRepository;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import ru.yandex.practicum.filmorate.repository.rating.RatingMpaRepository;
import ru.yandex.practicum.filmorate.repository.user.UserRepository;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class JdbcFilmService implements FilmService {

    FilmRepository filmRepository;
    GenreRepository genreRepository;
    RatingMpaRepository ratingMpaRepository;
    UserRepository userRepository;

    @Override
    public void checkFilm(int idFilm, String massageLog, String massageException) {
        if (filmRepository.findAllFilms().stream()
                .map(Film::getId)
                .noneMatch(id -> id == idFilm)) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + idFilm +  ".");
        }
    }

    @Override
    public void checkFilmBirthday(Film film) {
        if (film.getReleaseDate().isBefore(FILM_BIRTHDAY)) {
            log.warn("JdbcFilmRepository, createFilm, incorrect ReleaseDate - {}.", film.getReleaseDate());
            throw new ValidationException("Ошибка при добавлении фильма. Дата релиза фильма не может быть"
                    + " раньше 28 декабря 1895 года. Указанная дата - " + film.getReleaseDate() + ".");
        }
    }

    @Override
    public void checkUser(int idUser, String massageLog, String massageException) {
        if (userRepository.findAllUsers().stream()
                .map(User::getId)
                .noneMatch(id -> id == idUser)) {
            log.warn(massageLog);
            throw new NotFoundException(massageException + idUser +  ".");
        }
    }

    @Override
    public void checkRatingMpaGenres(Film film) {
        if (Objects.nonNull(film.getMpa())) {
            ratingMpaRepository.findRatingMpa(film.getMpa().getId())
                    .orElseThrow(() -> new ValidationException("В приложении не предусмотрено такое mpa"));
        }
        if (Objects.nonNull(film.getGenres())) {
            film.getGenres().stream()
                    .map(Genre::getId)
                    .forEach(id -> genreRepository.findGenre(id)
                            .orElseThrow(() -> {
                                log.warn("Передан несуществующий жанр");
                                return new ValidationException("Передан несуществующий жанр");
                            }));
        }
    }


    @Override
    public Film createFilm(Film film) {
        checkFilmBirthday(film);
        checkRatingMpaGenres(film);
        Film newFilm = filmRepository.createFilm(film);
        genreRepository.addFilmGenre(film.getId(), film.getGenres());
        return newFilm;
    }

    @Override
    public Film createLikeOfFilm(int idFilm, int idUser) {
        String massageLog = "FilmService, createLikeOfFilm.";
        String massageException = "Ошибка при попытке поставить фильму лайк. Не найден переданный id = ";
        checkFilm(idFilm, massageLog, massageException);
        checkUser(idUser, massageLog, massageException);
        if (filmRepository.findLikes(idFilm).stream()
                .anyMatch(id -> id == idUser)) {
            log.warn(massageLog);
            throw new DataException("Ошибка при попытке поставить фильму лайк. Такой лайк уже есть.");
        }
        filmRepository.createLikeOfFilm(idFilm, idUser);
        log.info("FilmService, createLikeOfFilm, like created.");
        return filmRepository.findFilm(idFilm);
    }

    @Override
    public Film deleteLikeOfFilm(int idFilm, int idUser) {
        String massageLog = "FilmService, deleteLikeOfFilm.";
        String massageException = "Ошибка при попытке удалить лайк, поставленный фильму. Не найден переданный id = ";
        checkFilm(idFilm, massageLog, massageException);
        checkUser(idUser, massageLog, massageException);
        if (filmRepository.findLikes(idFilm).stream()
                .noneMatch(id -> id == idUser)) {
            log.warn(massageLog);
            throw new NotFoundException("Ошибка при попытке удалить лайк, поставленный фильму. Такого лайка нет.");
        }
        filmRepository.deleteLikeOfFilm(idFilm, idUser);
        log.info("FilmService, deleteLikeOfFilm, like deleted.");
        return filmRepository.findFilm(idFilm);
    }

    @Override
    public Collection<Film> findAllFilms() {
        return filmRepository.findAllFilms().stream()
                .map(film -> {
                    Set<Genre> genres = genreRepository.findGenres(film.getId());
                    film.setGenres(genres);
                    RatingMpa ratingMpa = ratingMpaRepository.findRatingMpa(film.getIdRatingMpa()).get();
                    film.setMpa(ratingMpa);
                    return film;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Film findFilm(int idFilm) {
        Film film = filmRepository.findFilm(idFilm);
        film.setGenres(genreRepository.findGenres(idFilm));
        film.setMpa(ratingMpaRepository.findRatingMpa(film.getIdRatingMpa()).get());
        return film;
    }

    @Override
    public Collection<Film> findPopularFilms(int count) {
        if (count <= 0) {
            log.warn("FilmController, findPopularFilms, negative count = {}.", count);
            throw new ValidationException("Ошибка при попытке получить список популярных фильмов."
                    + " Передан отрицательный параметр count = " + count + ".");
        }
        return filmRepository.findAllFilms().stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .map(film -> {
                    Set<Genre> genres = genreRepository.findGenres(film.getId());
                    film.setGenres(genres);
                    RatingMpa ratingMpa = ratingMpaRepository.findRatingMpa(film.getIdRatingMpa()).get();
                    film.setMpa(ratingMpa);
                    return film;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Film updateFilm(Film film) {
        String massageLog = "FilmService, updateFilm.";
        String massageException = "Ошибка при попытке обновить фильм.";
        checkFilm(film.getId(), massageLog, massageException);
        checkFilmBirthday(film);
        checkRatingMpaGenres(film);
        genreRepository.deleteFilmGenres(film.getId());
        genreRepository.addFilmGenre(film.getId(), film.getGenres());
        return filmRepository.updateFilm(film);
    }

}