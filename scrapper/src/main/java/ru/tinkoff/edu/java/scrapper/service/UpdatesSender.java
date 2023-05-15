package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.model.bot.LinkUpdateRequest;

public interface UpdatesSender {
    void sendUpdate(LinkUpdateRequest request);
}
