package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.Optional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    FilmService filmService;
    static final String ID = "id";
    static final String USER_ID = "userId";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{" + ID + "}/like/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Film createLikeOfFilm(@PathVariable(ID) Optional<Integer> idFilm,
                                 @PathVariable(USER_ID) Optional<Integer> idUser) {
        if (!idFilm.isPresent()) {
            log.warn("FilmController, createLikeOfFilm, not found idFilm.");
            throw new ValidationException("Ошибка при попытке поставить фильму лайк. Не был указан id фильма.");
        }
        if (!idUser.isPresent()) {
            log.warn("FilmController, createLikeOfFilm, not found idUser.");
            throw new ValidationException("Ошибка при попытке поставить фильму лайк. Не был указан id пользователя.");
        }
        if (idFilm.get() < 0) {
            log.warn("FilmController, createLikeOfFilm, negative idFilm = {}.", idFilm.get());
            throw new ValidationException("Ошибка при попытке поставить фильму лайк. Был передан"
                    + " отрицательный id фильма = " + idFilm.get() + ".");
        }
        if (idUser.get() < 0) {
            log.warn("FilmController, createLikeOfFilm, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке поставить фильму лайк. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        return filmService.createLikeOfFilm(idFilm.get(), idUser.get());
    }

    @DeleteMapping("/{" + ID + "}/like/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Film deleteLikeOfFilm(@PathVariable(ID) Optional<Integer> idFilm,
                                 @PathVariable(USER_ID) Optional<Integer> idUser) {
        if (!idFilm.isPresent()) {
            log.warn("FilmController, deleteLikeOfFilm, not found idFilm.");
            throw new ValidationException("Ошибка при попытке удалить лайк, поставленный фильму."
                    + " Не был указан id фильма.");
        }
        if (!idUser.isPresent()) {
            log.warn("FilmController, deleteLikeOfFilm, negative idFilm = {}.", idFilm.get());
            throw new ValidationException("Ошибка при попытке удалить лайк, поставленный фильму."
                    + " Не был указан id пользователя.");
        }
        if (idFilm.get() < 0) {
            log.warn("FilmController, deleteLikeOfFilm, negative idFilm = {}.", idFilm.get());
            throw new ValidationException("Ошибка при попытке удалить лайк, поставленный фильму. Был передан"
                    + " отрицательный id фильма = " + idFilm.get() + ".");
        }
        if (idUser.get() < 0) {
            log.warn("FilmController, deleteLikeOfFilm, negative idUser = {}.", idUser.get());
            throw new ValidationException("Ошибка при попытке удалить лайк, поставленный фильму. Был передан"
                    + " отрицательный id пользователя = " + idUser.get() + ".");
        }
        return filmService.deleteLikeOfFilm(idFilm.get(), idUser.get());
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        if (count <= 0) {
            log.warn("FilmController, findPopularFilms, negative count = {}.", count);
            throw new ValidationException("Ошибка при попытке получить список популярных фильмов."
                    + " Передан отрицательный параметр count = " + count + ".");
        }
        return filmService.findPopularFilms(count);
    }

}