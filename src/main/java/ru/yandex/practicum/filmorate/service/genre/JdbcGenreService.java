package ru.yandex.practicum.filmorate.service.genre;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.genre.GenreRepository;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class JdbcGenreService implements GenreService {

    GenreRepository genreRepository;

    @Override
    public Collection<Genre> findAllGenres() {
        return genreRepository.findAllGenres();
    }

    @Override
    public Collection<Genre> findGenres(int idFilm) {
        return genreRepository.findGenres(idFilm);
    }

    @Override
    public Genre findGenre(int idGenre) {
        if (genreRepository.findAllGenres().stream()
                .map(Genre::getId)
                .noneMatch(id -> id == idGenre)) {
            log.error("JdbcRatingMpaService, findRatingMpa.");
            throw new NotFoundException("Ошибка при попытке получить рейтинг. Не найден переданный id = "
                    + idGenre + ".");
        }
        return genreRepository.findGenre(idGenre).get();
    }

}