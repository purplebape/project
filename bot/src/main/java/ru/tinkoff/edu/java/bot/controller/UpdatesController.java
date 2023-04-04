package ru.tinkoff.edu.java.bot.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.model.controller.LinkUpdateRequest;

@Validated
@RestController
public class UpdatesController {
    @PostMapping(
            path = "/updates",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void sendUpdates(@RequestBody LinkUpdateRequest request) {}
}