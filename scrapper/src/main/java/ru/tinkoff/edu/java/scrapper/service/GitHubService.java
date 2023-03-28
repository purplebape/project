package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.clients.githubClient.model.*;
import ru.tinkoff.edu.java.scrapper.clients.githubClient.*;

@RequiredArgsConstructor
@Service
public class GitHubService {
    private final GitHubClient gitHubClient;

    public Mono<GitHubRepositoryResponse> fetchRepository(String owner, String repo) {
        return gitHubClient.fetchRepo(owner, repo);
    }
}
