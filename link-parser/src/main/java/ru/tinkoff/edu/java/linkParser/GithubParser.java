package ru.tinkoff.edu.java.linkParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class GithubParser extends BaseParser {
    @Override
    public String[] parse(String link) {
        try {
            URL url = new URL(link);
            String host = url.getHost();
            String path = url.getPath();
            if (host.equals("github.com") && path.split("/").length == 3) {
                String[] parts = path.split("/");
                return Arrays.stream(parts)
                        .filter(part -> !part.isEmpty())
                        .toArray(String[]::new);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return parseNext(link);
    }
}
