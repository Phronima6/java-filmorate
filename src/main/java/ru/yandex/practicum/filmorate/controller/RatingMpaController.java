package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.service.rating.RatingMpaService;
import java.util.Collection;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingMpaController {

    static final String ID = "id";
    RatingMpaService ratingMpaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<RatingMpa> findAllRatingMpa() {
        return ratingMpaService.findAllRatingMpa();
    }

    @GetMapping("/{" + ID + "}")
    @ResponseStatus(HttpStatus.OK)
    public RatingMpa findRatingMpa(@PathVariable(ID) @Positive int idRatingMpa) {
        return ratingMpaService.findRatingMpa(idRatingMpa);
    }

}