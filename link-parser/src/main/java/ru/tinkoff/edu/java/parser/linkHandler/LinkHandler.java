package ru.tinkoff.edu.java.parser.linkHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.parser.linkData.LinkData;

public interface LinkHandler {
    @Nullable LinkData handleLink(@NotNull String link);
}
