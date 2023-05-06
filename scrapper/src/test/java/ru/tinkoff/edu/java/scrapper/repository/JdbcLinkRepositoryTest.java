package ru.tinkoff.edu.java.scrapper.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.model.view.Link;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;

import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    void add__addOne_oneAdded() {
        String url = "https://github.com/purplebape/project";

        int countBefore = getAll().size();
        linkRepository.add(url);
        int countAfter = getAll().size();

        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    @Transactional
    @Rollback
    void add__alreadyExist_throwsException() {
        String url = "https://github.com/purplebape/project";

        linkRepository.add(url);

        assertThrows(DuplicateKeyException.class, () -> linkRepository.add(url));
    }

    @Test
    @Transactional
    @Rollback
    void find__exists_returnsOne() {
        String url = "https://github.com/purplebape/project";
        Long id = createLink(url);

        Link link = linkRepository.find(url);

        assertEquals(link.getId(), id);
    }

    @Test
    @Transactional
    @Rollback
    void find__notExists_throwsException() {
        String url = "https://github.com/purplebape/project";

        assertThrows(EmptyResultDataAccessException.class, () -> linkRepository.find(url));
    }

    @Test
    @Transactional
    @Rollback
    void findById__exists_returnsOne() {
        String url = "https://github.com/purplebape/project";
        Long id = createLink(url);

        Link link = linkRepository.findById(id);

        assertEquals(link.getId(), id);
    }

    @Test
    @Transactional
    @Rollback
    void findById__notExists_throwsException() {
        Long id = 1L;

        assertThrows(EmptyResultDataAccessException.class, () -> linkRepository.findById(id));
    }

    @Test
    @Transactional
    @Rollback
    void findAll__nothingExists_zeroItemsReturned() {
        List<Link> all = linkRepository.findAll();

        assertEquals(all.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void findAll__oneExists_oneReturned() {
        String url = "https://github.com/purplebape/project";
        createLink(url);

        List<Link> all = linkRepository.findAll();

        assertEquals(all.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void findWithChatSubscription__exists_returnsNotEmpty() {
        String url = "https://github.com/purplebape/project";
        Long chatId = 1L;
        Long linkId = createLink(url);
        createChat(chatId);
        createSubscription(chatId, linkId);

        List<Link> linkEntities = linkRepository.findWithSubscriber(chatId);

        assertEquals(linkEntities.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void findWithChatSubscription__notExists_returnsEmpty() {
        Long chatId = 1L;

        List<Link> linkEntities = linkRepository.findWithSubscriber(chatId);

        assertEquals(linkEntities.size(), 0);
    }

    @Test
    @Transactional
    @Rollback
    void remove__oneExists_oneRemoved() {
        String url = "https://github.com/purplebape/project";
        createLink(url);

        int countBefore = getAll().size();
        int removed = linkRepository.remove(url);
        int countAfter = getAll().size();

        assertEquals(removed, 1);
        assertEquals(countBefore - 1, countAfter);
    }

    @Test
    @Transactional
    @Rollback
    void remove__notExists_zeroRemoved() {
        String url = "https://github.com/purplebape/project";

        int countBefore = getAll().size();
        int removed = linkRepository.remove(url);
        int countAfter = getAll().size();

        assertEquals(removed, 0);
        assertEquals(countBefore, countAfter);
    }

    @Test
    @Transactional
    @Rollback
    void removeById__exists_removed() {
        String url = "https://github.com/purplebape/project";
        Long id = createLink(url);

        int beforeCount = getAll().size();
        linkRepository.removeById(id);
        int afterCount = getAll().size();

        assertEquals(beforeCount - 1, afterCount);
    }

    @Test
    @Transactional
    @Rollback
    void removeById__notExists_notRemoved() {
        Long id = 1L;

        int beforeCount = getAll().size();
        linkRepository.removeById(id);
        int afterCount = getAll().size();

        assertEquals(beforeCount, afterCount);
    }

    @Test
    @Transactional
    @Rollback
    void removeWithZeroSubscriptions__doesNotHaveSub_removed() {
        String url = "https://github.com/purplebape/project";
        createLink(url);

        int beforeCount = getAll().size();
        int removed = linkRepository.removeWithZeroSubscribers();
        int afterCount = getAll().size();

        assertEquals(removed, 1);
        assertEquals(beforeCount - 1, afterCount);
    }

    @Test
    @Transactional
    @Rollback
    void removeWithZeroSubscriptions__allLinksHaveSub_nothingRemoved() {
        String url = "https://github.com/purplebape/project";
        Long chatId = 1L;
        Long linkId = createLink(url);
        createChat(chatId);
        createSubscription(chatId, linkId);

        int beforeCount = getAll().size();
        int removed = linkRepository.removeWithZeroSubscribers();
        int afterCount = getAll().size();

        assertEquals(removed, 0);
        assertEquals(beforeCount, afterCount);
    }

    private List<Link> getAll() {
        return template.query("select id, url from link", new BeanPropertyRowMapper<>(Link.class));
    }

    private Long createLink(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into link (url) values (?)", new String[] {"id"});
            ps.setString(1, url);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void createChat(Long id) {
        template.update("insert into chat (id) values (?)", id);
    }

    private void createSubscription(Long chatId, Long linkId) {
        template.update("insert into subscription (chat_id, link_id) values (?, ?)", chatId, linkId);
    }
}