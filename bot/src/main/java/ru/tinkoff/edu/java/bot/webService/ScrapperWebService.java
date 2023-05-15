package ru.tinkoff.edu.java.bot.webService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tinkoff.edu.java.bot.exception.ChatAlreadyRegisteredException;
import ru.tinkoff.edu.java.bot.exception.LinkIsAlreadyTackingException;
import ru.tinkoff.edu.java.bot.exception.LinkIsNotTrackingException;
import ru.tinkoff.edu.java.bot.exception.ScrapperInternalError;
import ru.tinkoff.edu.java.bot.model.scrapper.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.webClient.ScrapperWebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapperWebService {
    private final ScrapperWebClient client;

    public void createChat(Long id) throws ChatAlreadyRegisteredException {
        try {
            client.createChat(id);
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new ChatAlreadyRegisteredException(ex);
            }
            throw new ScrapperInternalError(ex);
        }
    }

    public void deleteChat(Long id) {
        client.deleteChat(id);
    }

    public LinkResponse createLink(Long id, String link) throws LinkIsAlreadyTackingException {
        try {
            return client.createLink(id, new AddLinkRequest(link));
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new LinkIsAlreadyTackingException(ex);
            }
            throw new ScrapperInternalError(ex);
        }
    }

    public ListLinksResponse getAllLinks(Long id) {
        return client.getAllLinks(id);
    }

    public LinkResponse deleteLink(Long id, String link) throws LinkIsNotTrackingException {
        try {
            return client.deleteLink(id, new RemoveLinkRequest(link));
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new LinkIsNotTrackingException(ex);
            }
            throw new ScrapperInternalError(ex);
        }
    }
}
