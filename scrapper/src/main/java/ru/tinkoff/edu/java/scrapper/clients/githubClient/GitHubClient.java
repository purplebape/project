package ru.tinkoff.edu.java.scrapper.clients.githubClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.clients.githubClient.model.GitHubRepositoryResponse;

@HttpExchange(
        accept = MediaType.APPLICATION_JSON_VALUE,
        contentType = MediaType.APPLICATION_JSON_VALUE
)
public interface GitHubClient {
    @GetMapping(
            value = "/repos/{owner}/{repo}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Mono<GitHubRepositoryResponse> fetchRepo(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    );
}

