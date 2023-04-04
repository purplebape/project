package ru.tinkoff.edu.java.parser.handler;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.linkParser.linkData.LinkData;
import ru.tinkoff.edu.java.linkParser.linkHandler.ChainLinkHandler;
import ru.tinkoff.edu.java.linkParser.linkHandler.GitHubLinkHandler;
import ru.tinkoff.edu.java.linkParser.linkHandler.LinkHandler;
import ru.tinkoff.edu.java.linkParser.linkHandler.StackOverflowLinkHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChainLinkHandlerTest {
    private final ChainLinkHandler chainLinkHandler;

    ChainLinkHandlerTest() {
        List<LinkHandler> handlers = Arrays.asList(
                new GitHubLinkHandler(),
                new StackOverflowLinkHandler()
        );
        chainLinkHandler = new ChainLinkHandler(handlers);
    }

    @ParameterizedTest
    @MethodSource({
            "getParametersWrongFormat",
            "getParametersWrongDomain",
            "getParametersCorrect"
    })
    void handle__incorrectLink_returnNull(String link, boolean correct) {
        // given

        // when
        LinkData data = chainLinkHandler.handle(link);

        // then
        assertEquals(data != null, correct);
    }

    private Stream<Arguments> getParametersCorrect() {
        return Stream.of(
                Arguments.of("https://github.com/purplebape/project", true),
                Arguments.of("https://stackoverflow.com/questions/75932005/postman-http-post-json-body-and-file-upload", true),
                Arguments.of("https://stackoverflow.com/questions/75929687/html-elements-by-yandex-qa-tools-with-spring-framework", true)
        );
    }

    private Stream<Arguments> getParametersWrongFormat() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of("1", false),
                Arguments.of("github.com/", false),
                Arguments.of("https://github.com/", false),
                Arguments.of("https://github.com/purplebape", false),
                Arguments.of("https://stackoverflow.com/123123/123123", false)
        );
    }

    private Stream<Arguments> getParametersWrongDomain() {
        return Stream.of(
                Arguments.of("https://stackoverflow.random/questions/9706688/what", false)
        );
    }
}