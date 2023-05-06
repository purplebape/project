package ru.tinkoff.edu.java.bot.telegram;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.edu.java.bot.webService.ScrapperWebService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties="spring.main.lazy-initialization=true")
@TestPropertySource(locations = "classpath:application.yaml")
class MessageHandlerTest {
    @MockBean
    private ScrapperWebService service;

    @Autowired
    private MessageHandler messageHandler;

    @Test
    void handle__unknownCommand_returnSpecialMessage() {
        SendMessage response = messageHandler.handle(createMessage("SomeCommandThadDoesNotExist"));

        assertEquals(response.getText(), "Команда неизвестна. Нажмите /help, чтобы посмотреть список команд.");
    }

    private Message createMessage(String text) {
        Message message = new Message();
        message.setChat(new Chat(1L, "private"));
        message.setText(text);
        return message;
    }
}
