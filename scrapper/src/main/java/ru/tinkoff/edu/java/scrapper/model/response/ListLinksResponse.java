package ru.tinkoff.edu.java.scrapper.model.response;

import java.util.List;
public record ListLinksResponse(
        List<LinkResponse> links,
        Integer size
) {}
