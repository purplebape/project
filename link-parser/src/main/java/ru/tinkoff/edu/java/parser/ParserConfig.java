package ru.tinkoff.edu.java.parser;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.linkHandler.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.linkHandler.LinkHandler;
import ru.tinkoff.edu.java.parser.linkHandler.LinkHandlerChain;
import ru.tinkoff.edu.java.parser.linkHandler.StackOverflowLinkHandler;

@Configuration
public class ParserConfig {
    @Bean
    public StackOverflowLinkHandler stackOverflowLinkHandler() {
        return new StackOverflowLinkHandler();
    }

    @Bean
    public GitHubLinkHandler gitHubLinkHandler() {
        return new GitHubLinkHandler();
    }

    public @Bean LinkHandlerChain linkHandlerChain(List<LinkHandler> handlers) {
        return new LinkHandlerChain(handlers);
    }
}
