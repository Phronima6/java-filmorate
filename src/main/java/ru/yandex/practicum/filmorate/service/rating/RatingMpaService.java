package ru.yandex.practicum.filmorate.service.rating;

import ru.yandex.practicum.filmorate.model.RatingMpa;
import java.util.Collection;

public interface RatingMpaService {

    // Получение всех рейтингов фильмов
    Collection<RatingMpa> findAllRatingMpa();

    // Получение рейтинга определённого фильма
    RatingMpa findRatingMpa(int idRatingMpa);

}