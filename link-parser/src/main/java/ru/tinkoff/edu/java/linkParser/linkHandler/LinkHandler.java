package ru.tinkoff.edu.java.linkParser.linkHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.linkParser.linkData.LinkData;

public interface LinkHandler {
    @Nullable LinkData handleLink(@NotNull String link);
}

