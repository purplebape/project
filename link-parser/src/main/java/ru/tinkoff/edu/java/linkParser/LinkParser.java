package ru.tinkoff.edu.java.linkParser;

import java.net.MalformedURLException;

public interface LinkParser {
    String[] parse(String link);
    void setNextParser(LinkParser nextParser);
}
