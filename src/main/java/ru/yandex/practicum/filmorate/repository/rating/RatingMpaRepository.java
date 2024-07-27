package ru.yandex.practicum.filmorate.repository.rating;

import ru.yandex.practicum.filmorate.model.RatingMpa;
import java.util.Collection;
import java.util.Optional;

public interface RatingMpaRepository {

    // Получение всех рейтингов фильмов
    Collection<RatingMpa> findAllRatingMpa();

    // Получение рейтинга определённого фильма
    Optional<RatingMpa> findRatingMpa(int idRatingMpa);

}