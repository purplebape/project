package ru.tinkoff.edu.java.scrapper.model.response;

import java.util.List;

public record ApiErrorResponse(
        String description,
        String code,
        String exceptionName,
        String exceptionMessage,
        List<String> stacktrace
) {}
