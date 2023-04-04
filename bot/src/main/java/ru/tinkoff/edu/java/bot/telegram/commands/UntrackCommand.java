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

@Order(5)
@Slf4j
@Component
public class UntrackCommand extends AbstractPublicCommand {
    private final ScrapperWebService webService;

    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "прекратить отслеживание ссылки";
    private static final Pattern PATTERN = Pattern.compile("^\\s*/untrack (\\S+)\\s*$");
    private static final String SUCCESS_RESPONSE = "Ссылка удалена из списка.";
    private static final String WRONG_FORMAT_RESPONSE = "Используйте правильный формат: /untrack <link>";

    public UntrackCommand(ScrapperWebService webService) {
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
        log.info("Ссылка удалена {}", url);
        webService.createLink(message.getChatId(), url);
        return new SendMessage(message.getChatId().toString(), SUCCESS_RESPONSE);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}


