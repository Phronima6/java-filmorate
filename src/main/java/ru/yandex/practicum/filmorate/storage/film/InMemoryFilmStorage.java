package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        if (!film.getReleaseDate().isBefore(FILM_BIRTHDAY)) {
            film.setId(getNextId());
            log.info("InMemoryFilmStorage, createFilm, idFilm created = {}.", film.getId());
            films.put(film.getId(), film);
        } else {
            log.warn("InMemoryFilmStorage, createFilm, incorrect ReleaseDate - {}.", film.getReleaseDate());
            throw new ValidationException("Ошибка при добавлении фильма. Дата релиза фильма не может быть"
                    + " раньше 28 декабря 1895 года. Указанная дата - " + film.getReleaseDate() + ".");
        }
        log.info("InMemoryFilmStorage, createFilm, Film created.");
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("InMemoryFilmStorage, updateFilm, not found idFilm = {}.", film.getId());
            throw new NotFoundException("Ошибка при попытке обновить пользователя. Не найден переданный"
                    + " id фильма = " + film.getId() + ".");
        }
        log.info("InMemoryFilmStorage, updateFilm, Film update.");
        return film;
    }

    @Override
    public int getNextId() {
        int currentMaxId = films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}