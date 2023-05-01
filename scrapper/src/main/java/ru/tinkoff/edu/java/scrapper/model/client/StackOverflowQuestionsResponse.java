package ru.tinkoff.edu.java.scrapper.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.tinkoff.edu.java.scrapper.model.client.StackOverflowQuestionResponse;

import java.util.List;
public record StackOverflowQuestionsResponse(
        List<StackOverflowQuestionResponse> items,
        @JsonProperty("has_more")
        Boolean hasMore
) {}