package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.model.stackoverflow.StackOverflowQuestionsResponse;
@HttpExchange(
        accept = MediaType.APPLICATION_JSON_VALUE,
        contentType = MediaType.APPLICATION_JSON_VALUE
)
public interface StackOverflowClient {
    @GetMapping(
            value = "/questions/{id}?site=stackoverflow",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Mono<StackOverflowQuestionsResponse> fetchQuestion(@PathVariable("id") Integer id);

    @GetMapping(
            value = "/questions/{ids}?site=stackoverflow",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Mono<StackOverflowQuestionsResponse> fetchQuestions(@PathVariable("ids") String ids);
}
