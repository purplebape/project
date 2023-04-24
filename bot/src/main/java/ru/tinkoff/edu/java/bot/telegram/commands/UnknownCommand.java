package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class UnknownCommand implements Command {
    private static final String UNKNOWN_COMMAND_RESPONSE =
            "Команда неизвестна. Нажмите /help, чтобы посмотреть список команд.";

    @Override
    public SendMessage handle(@NotNull Message message) {
        return new SendMessage(message.getChatId().toString(), UNKNOWN_COMMAND_RESPONSE);
    }

    @Override
    public boolean supports(@NotNull Message message) {
        return true;
    }
}