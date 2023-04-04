package ru.tinkoff.edu.java.scrapper.model.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tg-chat")
@Validated
public class TgChatController {
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChat(@PathVariable Long id) {
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(@PathVariable Long id) {
    }
}
