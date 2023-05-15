package ru.tinkoff.edu.java.scrapper.model.controller;

import jakarta.validation.constraints.NotBlank;

public record AddLinkRequest(@NotBlank String link) {
}
