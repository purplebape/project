package ru.tinkoff.edu.java.scrapper.model.controller;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, Integer size) {}
