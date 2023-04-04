package ru.tinkoff.edu.java.linkParser.linkHandler;


import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.linkParser.linkData.LinkData;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import ru.tinkoff.edu.java.linkParser.linkData.GitHubLinkData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(1)
@Component
public final class GitHubLinkHandler implements LinkHandler {
    private final Pattern pattern = Pattern.compile(
            "^https://github\\.com/([^/]+)/([^/]+)/?$"
    );

    @Override
    public @Nullable LinkData handleLink(@NonNull String link) {
        Matcher matcher = pattern.matcher(link);
        if (!matcher.matches()) {
            return null;
        }
        String username = getUsernameFromLink(matcher);
        String repository = getRepositoryFromLink(matcher);
        return new GitHubLinkData(username, repository);
    }

    private @NonNull String getUsernameFromLink(@NonNull Matcher matcher) {
        return matcher.group(1);
    }

    private @NonNull String getRepositoryFromLink(@NonNull Matcher matcher) {
        return matcher.group(2);
    }
}



