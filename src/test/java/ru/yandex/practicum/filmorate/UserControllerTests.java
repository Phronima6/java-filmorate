package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests {

    private final UserController userController = new UserController();
    private final User user = new User();
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();

    @Test // Проверка корректности работы исключения при попытке обновления пользователя с несуществующим id
    public void updateIdFail() {
        user.setBirthday(LocalDate.of(1955, 5, 19));
        user.setEmail("practicum@ya.ru");
        user.setId(100);
        user.setLogin("J.Gosling");
        user.setName("James Arthur Gosling");
        Assertions.assertThrows(NotFoundException.class, () -> userController.update(user),
                "Ошибка, исключение о некорректном id пользователя не выдано.");
    }

    @Test // Проверка корректности работы jakarta.validation при правильном запросе на добавление пользователя
    public void validUserThenOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON).content("{\"birthday\":\"1955-05-19\",\"email\":"
                        + "\"practicum@ya.ru\",\"login\":\"J.Gosling\",\"name\":\"James Arthur Gosling\"}"))
                .andExpect(status().isOk());
    }

    @Test // Проверка корректности работы jakarta.validation при некорректном запросе на добавление пользователя
    public void invalidUserThenBadRequest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON).content("{\"birthday\":\"2955-05-19\",\"email\":"
                        + "\"practicum\",\"login\":\"\",\"name\":\"James Arthur Gosling\"}"))
                .andExpect(status().isBadRequest());
    }

}