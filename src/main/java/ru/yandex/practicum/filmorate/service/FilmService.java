package ru.yandex.practicum.filmorate.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class FilmService {

    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

    public Collection<Film> findAllFilms() {
        return inMemoryFilmStorage.findAllFilms();
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

    // Ставит лайк фильму выбранному пользователем
    public Film createLikeOfFilm(int idFilm, int idUser) {
        if (!inMemoryFilmStorage.getFilms().containsKey(idFilm)) {
            log.warn("FilmService, createLikeOfFilm, not found idFilm.");
            throw new NotFoundException("Ошибка при попытке поставить фильму лайк. Не найден переданный"
                    + " id фильма = " + idFilm +  ".");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("FilmService, createLikeOfFilm, not found idUser.");
            throw new NotFoundException("Ошибка при попытке поставить фильму лайк. Не найден переданный"
                    + " id пользователя = " + idUser + ".");
        }
        inMemoryFilmStorage.getFilms().get(idFilm).getLikes().add(idUser);
        log.info("FilmService, createLikeOfFilm, like created.");
        return inMemoryFilmStorage.getFilms().get(idFilm);
    }

    // Удаляет лайк пользователя, поставленный фильму
    public Film deleteLikeOfFilm(int idFilm, int idUser) {
        if (!inMemoryFilmStorage.getFilms().containsKey(idFilm)) {
            log.warn("FilmService, deleteLikeOfFilm, not found idFilm.");
            throw new NotFoundException("Ошибка при попытке удалить лайк, поставленный фильму. Не найден переданный"
                    + " id фильма = " + idFilm +  ".");
        }
        if (!inMemoryUserStorage.getUsers().containsKey(idUser)) {
            log.warn("FilmService, deleteLikeOfFilm, not found idUser.");
            throw new NotFoundException("Ошибка при попытке удалить лайк, поставленный фильму. Не найден переданный"
                    + " id пользователя = " + idUser + ".");
        }
        inMemoryFilmStorage.getFilms().get(idFilm).getLikes().remove(idUser);
        log.info("FilmService, deleteLikeOfFilm, like deleted.");
        return inMemoryFilmStorage.getFilms().get(idFilm);
    }

    // Получение списка из первых count фильмов, отсортированных по количеству лайков
    public Collection<Film> findPopularFilms(int count) {
        if (inMemoryFilmStorage.getFilms().isEmpty()) {
            log.warn("FilmService, findPopularFilms, HashMap isEmpty.");
            throw new NotFoundException("Ошибка при попытке получить список из первых " + count + " фильмов." +
                    " Список фильмов пуст.");
        }
        return inMemoryFilmStorage.findAllFilms().stream()
                .sorted(Comparator.comparing((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

}