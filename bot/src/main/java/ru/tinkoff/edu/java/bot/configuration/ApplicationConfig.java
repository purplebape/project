package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@Validated
@Data
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Configuration
@Import({TelegramBotStarterConfiguration.class})
public class ApplicationConfig {
    @NotNull
    private String test;
    @NotNull
    private Bot bot;
    @NotNull
    private Scrapper scrapper;

    @Validated
    @Data
    public static class Bot {
        @NotBlank
        private String token;
        @NotBlank
        private String name;
    }

    @Validated
    @Data
    public static class Scrapper {
        @NotBlank
        private String url;
    }
}