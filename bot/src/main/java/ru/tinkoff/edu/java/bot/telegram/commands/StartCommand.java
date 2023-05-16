package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.exception.ChatAlreadyRegisteredException;
import ru.tinkoff.edu.java.bot.webService.ScrapperWebService;

@Order(3)
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperWebService webService;

    private static final String COMMAND = "/start";
    private static final String WELCOME_MESSAGE_RESPONSE = "Привет!";
    private static final String CHAT_ALREADY_REGISTERED_RESPONSE =
            "Вы уже запустили бота!";

    @Override
    public SendMessage handle(@NotNull Message message) {
        try {
            webService.createChat(message.getChatId());
        } catch (ChatAlreadyRegisteredException ignored) {
            return new SendMessage(message.getChatId().toString(), CHAT_ALREADY_REGISTERED_RESPONSE);
        }
        return new SendMessage(message.getChatId().toString(), WELCOME_MESSAGE_RESPONSE);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return message.getText().trim().startsWith(COMMAND);
    }
}
