package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.webService.ScrapperWebService;

@Order(3)
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperWebService webService;

    private static final String COMMAND = "/start";
    private static final String WELCOME_MESSAGE = "Привет!";

    @Override
    public SendMessage handle(@NotNull Message message) {
        try {
            webService.createChat(message.getChatId());
            return new SendMessage(message.getChatId().toString(), WELCOME_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании нового чата", e);
        }
    }

    @Override
    public boolean supports(@NotNull Message message) {
        try {
            return message.getText().trim().startsWith(COMMAND);
        } catch (Exception e) {
            return false;
        }
    }
}
