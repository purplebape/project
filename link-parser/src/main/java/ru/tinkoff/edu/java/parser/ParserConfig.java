package ru.tinkoff.edu.java.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.parser.linkHandler.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.linkHandler.LinkHandler;
import ru.tinkoff.edu.java.parser.linkHandler.ChainLinkHandler;
import ru.tinkoff.edu.java.parser.linkHandler.StackOverflowLinkHandler;

import java.util.List;

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

    public @Bean ChainLinkHandler linkHandlerChain(List<LinkHandler> handlers) {
        return new ChainLinkHandler(handlers);
    }
}