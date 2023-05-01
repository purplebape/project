package ru.tinkoff.edu.java.parser.linkHandler;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.parser.linkData.LinkData;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ChainLinkHandler {
    private final List<LinkHandler> handlers;

    public @Nullable LinkData handle(@NotNull String link) {
        return handlers.stream()
                .map(handler -> handler.handleLink(link))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}