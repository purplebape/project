package ru.tinkoff.edu.java.scrapper.service.domain.api;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.model.view.Link;

public interface LinkService {
    List<Link> updateLastCheckedTimeAndGet(Duration linkToBeCheckedInterval);

    void updateLinkLastUpdateTime(Long id, OffsetDateTime newUpdateTime);
}
