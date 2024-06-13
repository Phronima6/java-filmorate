package ru.yandex.practicum.filmorate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FilmControllerTests {

    FilmController filmController = new FilmController();
    Film film = new Film();
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new FilmController()).build();

    @Test // Проверка корректности работы исключения при попытке добавления фильма с датой раньше 28 декабря 1895 года
    public void createReleaseDateFail() {
        film.setDescription("Когда засуха, пыльные бури...");
        film.setDuration(169);
        film.setName("Интерстеллар");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film),
                "Ошибка, исключение о некорректной дата релиза фильма не выдано.");
    }

    @Test // Проверка корректности работы исключения при попытке обновления фильма с несуществующим id
    public void updateIdFail() {
        film.setDescription("Когда засуха, пыльные бури...");
        film.setDuration(169);
        film.setId(100);
        film.setName("Интерстеллар");
        film.setReleaseDate(LocalDate.of(2014, 10, 26));
        Assertions.assertThrows(NotFoundException.class, () -> filmController.update(film),
                "Ошибка, исключение о некорректном id фильма не выдано.");
    }

    @Test // Проверка корректности работы jakarta.validation при правильном запросе на добавление фильма
    public void validFilmThenOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON).content("{\"description\":\"Когда засуха, пыльные бури...\","
                        + "\"duration\":169,\"name\":\"Интерстеллар\",\"releaseDate\":\"2014-10-26\"}"))
                .andExpect(status().isOk());
    }

    @Test // Проверка корректности работы jakarta.validation при некорректном запросе на добавление фильма
    public void invalidFilmThenBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/films")
                .contentType(MediaType.APPLICATION_JSON).content("{\"description\":\"Когда засуха, пыльные бури"
                        + " и вымирание растений приводят человечество к продовольственному кризису, коллектив"
                        + " исследователей и учёных отправляется сквозь червоточину (которая предположительно"
                        + " соединяет области пространства-времени через большое расстояние) в путешествие,"
                        + " чтобы превзойти прежние ограничения для космических путешествий человека и найти"
                        + " планету с подходящими для человечества условиями.\","
                        + "\"duration\":-10,\"name\":\"\",\"releaseDate\":\"1895-01-01\"}"))
                .andExpect(status().isBadRequest());
    }

}