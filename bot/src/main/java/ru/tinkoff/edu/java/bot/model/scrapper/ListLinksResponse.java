package ru.tinkoff.edu.java.bot.model.scrapper;

import java.util.List;

public record ListLinksResponse(
        List<LinkResponse> links,
        Integer size
) {
}
