package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.webService.ScrapperWebService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(4)
@Slf4j
@Component
public class TrackCommand extends AbstractPublicCommand {
    private final ScrapperWebService webService;
    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "начать отслеживание ссылки";
    private static final Pattern PATTERN = Pattern.compile("^\\s*/track (\\S+)\\s*$");
    private static final String SUCCESS_RESPONSE = "Ссылка добавлена в список.";
    private static final String WRONG_FORMAT_RESPONSE = "Используйте правильный формат: /track <link>";

    public TrackCommand(ScrapperWebService webService) {
        super(COMMAND, DESCRIPTION);
        this.webService = webService;
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        String text = message.getText();
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            return new SendMessage(message.getChatId().toString(), WRONG_FORMAT_RESPONSE);
        }
        String url = matcher.group(1);
        log.info("Создана ссылка {}", url);
        webService.createLink(message.getChatId(), url);
        return new SendMessage(message.getChatId().toString(), SUCCESS_RESPONSE);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}