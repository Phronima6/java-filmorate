package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {

    FilmService filmService;
    static final String ID = "id";
    static final String USER_ID = "userId";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }


    @PutMapping("/{" + ID + "}/like/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Film createLikeOfFilm(@PathVariable(ID) @Positive int idFilm,
                                 @PathVariable(USER_ID) @Positive int idUser) {
        return filmService.createLikeOfFilm(idFilm, idUser);
    }

    @DeleteMapping("/{" + ID + "}/like/{" + USER_ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Film deleteLikeOfFilm(@PathVariable(ID) @Positive int idFilm,
                                 @PathVariable(USER_ID) @Positive int idUser) {
        return filmService.deleteLikeOfFilm(idFilm, idUser);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{" + ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public Film findFilm(@PathVariable(ID) @Positive int idFilm) {
        return filmService.findFilm(idFilm);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.findPopularFilms(count);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

}