package ru.tinkoff.edu.java.linkParser;

import java.net.MalformedURLException;
import java.net.URL;

public class StackOverflowParser extends BaseParser {
    @Override
    public String[] parse(String link) {
        try {
            URL url = new URL(link);
            String host = url.getHost();
            String path = url.getPath();
            if (host.equals("stackoverflow.com") && path.startsWith("/questions/")) {
                String[] parts = path.split("/");
                return new String[] { parts[2] };
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return parseNext(link);
    }
}
