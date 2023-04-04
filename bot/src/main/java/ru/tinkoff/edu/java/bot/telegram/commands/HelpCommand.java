package ru.tinkoff.edu.java.bot.telegram.commands;

import jakarta.validation.constraints.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Order(1)
public class HelpCommand extends AbstractPublicCommand {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "показать список команд";

    public HelpCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(@NotNull Message message) {
        StringBuilder commandList = new StringBuilder();
        commandList.append("/start - зарегистрировать пользователя\n")
                .append("/help - вывести окно с командами\n")
                .append("/track - начать отслеживание ссылки\n")
                .append("/untrack - прекратить отслеживание ссылки\n")
                .append("/list - показать список отслеживаемых ссылок");
        return new SendMessage(message.getChatId().toString(), commandList.toString());
    }

    @Override
    public boolean supports(@NotNull Message message) {
        String text = message.getText().trim();
        return text.startsWith(COMMAND);
    }

}

