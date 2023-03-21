package ru.tinkoff.edu.java.linkParser;

public abstract class BaseParser implements LinkParser {
    private LinkParser nextParser;

    @Override
    public void setNextParser(LinkParser nextParser) {
        this.nextParser = nextParser;
    }

    protected String[] parseNext(String link) {
        if (nextParser != null) {
            return nextParser.parse(link);
        }
        return null;
    }
}
