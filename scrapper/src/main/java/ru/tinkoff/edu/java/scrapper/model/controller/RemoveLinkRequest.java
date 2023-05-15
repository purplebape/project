package ru.tinkoff.edu.java.scrapper.model.controller;

import jakarta.validation.constraints.NotBlank;

public record RemoveLinkRequest(@NotBlank String link) {}
