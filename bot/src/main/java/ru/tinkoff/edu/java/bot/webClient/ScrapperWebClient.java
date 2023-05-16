package ru.tinkoff.edu.java.bot.webClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.edu.java.bot.model.scrapper.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.RemoveLinkRequest;

@HttpExchange(
        accept = MediaType.APPLICATION_JSON_VALUE,
        contentType = MediaType.APPLICATION_JSON_VALUE
)
public interface ScrapperWebClient {
    @PostExchange("/tg-chat/{id}")
    void createChat(@PathVariable("id") Long id);

    @DeleteExchange("/tg-chat/{id}")
    void deleteChat(@PathVariable("id") Long id);

    @PostExchange("/links/{id}")
    LinkResponse createLink(
            @PathVariable("id") Long id,
            @RequestBody AddLinkRequest request
    );

    @GetExchange("/links/{id}")
    ListLinksResponse getAllLinks(@PathVariable("id") Long id);

    @DeleteExchange("/links/{id}")
    LinkResponse deleteLink(
            @PathVariable("id") Long id,
            @RequestBody RemoveLinkRequest request
    );
}
