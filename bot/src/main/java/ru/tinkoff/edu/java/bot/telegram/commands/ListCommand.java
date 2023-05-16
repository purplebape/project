package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.model.scrapper.LinkResponse;
import ru.tinkoff.edu.java.bot.model.scrapper.ListLinksResponse;
import ru.tinkoff.edu.java.bot.webService.ScrapperWebService;

@Order(2)
@Component
public class ListCommand extends AbstractPublicCommand {
    private final ScrapperWebService webService;

    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "показать список отслеживаемых ссылок";
    private static final String EMPTY_LINKS_LIST_MESSAGE =
            "Отслеживаемых ссылок ещё нет. Введите /track <ссылка> для добавления.";

    public ListCommand(ScrapperWebService webService) {
        super(COMMAND, DESCRIPTION);
        this.webService = webService;
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        ListLinksResponse response = webService.getAllLinks(message.getChatId());
        String text = response.size() == 0 ? EMPTY_LINKS_LIST_MESSAGE : getFormattedText(response);
        return new SendMessage(message.getChatId().toString(), text);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }

    private String getFormattedText(ListLinksResponse response) {
        List<String> links = response
                .links()
                .stream()
                .map(LinkResponse::link)
                .map(URI::toString)
                .toList();
        return "Список текущих отслеживаемых ссылок: \n" + links
                .stream()
                .map(link -> "- " + link)
                .collect(Collectors.joining("\n"));
    }
}
