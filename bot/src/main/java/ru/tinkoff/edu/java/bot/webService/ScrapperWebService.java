package ru.tinkoff.edu.java.bot.webService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.webClient.ScrapperWebClient;
import ru.tinkoff.edu.java.bot.model.scrapper.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.RemoveLinkRequest;

@RequiredArgsConstructor
@Service
public class ScrapperWebService {
    private final ScrapperWebClient client;

    public void createChat(Long id) {
        client.createChat(id);
    }

    public void deleteChat(Long id) {
        client.deleteChat(id);
    }

    public LinkResponse createLink(Long id, String link) {
        return client.createLink(id, new AddLinkRequest(link));
    }

    public ListLinksResponse getAllLinks(Long id) {
        return client.getAllLinks(id);
    }

    public LinkResponse deleteLink(Long id, String link) {
        return client.deleteLink(id, new RemoveLinkRequest(link));
    }
}

