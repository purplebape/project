package ru.tinkoff.edu.java.parser.linkHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.tinkoff.edu.java.parser.linkData.GitHubLinkData;
import ru.tinkoff.edu.java.parser.linkData.LinkData;

public final class GitHubLinkHandler implements LinkHandler {
    private final Pattern pattern = Pattern.compile(
        "^https://github\\.com/([^/]+)/([^/]+)/?$"
    );

    @Override
    public @Nullable LinkData handleLink(@NotNull String link) {
        Matcher matcher = pattern.matcher(link);
        if (!matcher.matches()) {
            return null;
        }
        String username = matcher.group(1);
        String repository = matcher.group(2);
        return new GitHubLinkData(username, repository);
    }
}
