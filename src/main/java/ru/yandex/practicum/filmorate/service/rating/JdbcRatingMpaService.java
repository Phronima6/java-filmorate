package ru.yandex.practicum.filmorate.service.rating;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.repository.rating.RatingMpaRepository;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class JdbcRatingMpaService implements RatingMpaService {

    RatingMpaRepository ratingMpaRepository;

    @Override
    public Collection<RatingMpa> findAllRatingMpa() {
        return  ratingMpaRepository.findAllRatingMpa();
    }

    @Override
    public RatingMpa findRatingMpa(int idRatingMpa) {
        if (ratingMpaRepository.findAllRatingMpa().stream()
                .map(RatingMpa::getId)
                .noneMatch(id -> id == idRatingMpa)) {
            log.error("JdbcRatingMpaService, findRatingMpa.");
            throw new NotFoundException("Ошибка при попытке получить жанр. Не найден переданный id = "
                    + idRatingMpa + ".");
        }
        return  ratingMpaRepository.findRatingMpa(idRatingMpa).get();
    }

}