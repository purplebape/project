package ru.tinkoff.edu.java.scrapper.service.domain.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.service.domain.api.ChatService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaChatServiceTest extends IntegrationEnvironment {
    @Autowired
    private ChatService chatService;

    @Autowired
    private ServicesTestHelper helper;

    @Test
    @Transactional
    @Rollback
    void register__chatDoesNotExist_registersChat() {
        Long id = 1L;
        assertNull(helper.getChatById(id));

        chatService.register(id);

        assertNotNull(helper.getChatById(id));
    }

    @Test
    @Transactional
    @Rollback
    void register__chatExists_throwsException() {
        Long id = 1L;
        helper.addChat(id);

        assertNotNull(helper.getChatById(id));
        assertThrows(IllegalArgumentException.class, () -> chatService.register(id));
    }

    @Test
    @Transactional
    @Rollback
    void unregister__chatExists_unregistersChat() {
        Long id = 1L;
        helper.addChat(id);

        assertNotNull(helper.getChatById(id));
        chatService.unregister(id);
        assertNull(helper.getChatById(id));
    }

    @Test
    @Transactional
    @Rollback
    void unregister__chatDoesNotExist_throwsException() {
        Long id = 1L;

        assertNull(helper.getChatById(id));
        assertThrows(IllegalArgumentException.class, () -> chatService.unregister(id));
    }

    @Test
    @Transactional
    @Rollback
    void unregister__linkHasOnlySubscriber_deletesLink() {
        Long chatId = 1L;
        Long linkId = 1L;
        String url = "https://github.com/purplebape/project/";
        helper.addChat(chatId);
        helper.addLink(linkId, url);
        helper.addSubscription(chatId, linkId);

        assertNotNull(helper.getChatById(chatId));
        assertNotNull(helper.getLinkById(linkId));
        assertNotNull(helper.getSubscriptionById(chatId, linkId));
        chatService.unregister(chatId);
        assertNull(helper.getChatById(chatId));
        assertNull(helper.getLinkById(linkId));
        assertNull(helper.getSubscriptionById(chatId, linkId));
    }
}
