package ru.tinkoff.edu.java.bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.tinkoff.edu.java.bot.webClient.ScrapperWebClient;

@Configuration
public class WebClientConfiguration {
    private final ExchangeStrategies exchangeStrategies;

    public WebClientConfiguration(ObjectMapper objectMapper) {
        exchangeStrategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                }).build();
    }

    @Bean
    public ScrapperWebClient gitHubWebClient(ApplicationConfiguration config) {
        return buildWebClient(config.getScrapper().getUrl(), ScrapperWebClient.class);
    }

    private <T> T buildWebClient(String baseUrl, Class<T> client) {
        WebClient webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(baseUrl)
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        return httpServiceProxyFactory.createClient(client);
    }
}
