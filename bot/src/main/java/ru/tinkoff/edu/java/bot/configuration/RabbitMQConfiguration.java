package ru.tinkoff.edu.java.bot.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.listener.ScrapperQueueListener;
import ru.tinkoff.edu.java.bot.model.controller.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.telegram.TrackerBot;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "rabbit-queue")
public class RabbitMQConfiguration {
    private final String exchangeName;
    private final String queueName;
    private final String routingKey;

    private final static String DLQ_SUFFIX = ".dlq";

    public RabbitMQConfiguration(ApplicationConfiguration config) {
        this.exchangeName = config.getRabbitQueue().getExchangeName();
        this.queueName = config.getRabbitQueue().getQueueName();
        this.routingKey = config.getRabbitQueue().getRoutingKey();
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.model.bot.LinkUpdateRequest", LinkUpdateRequest.class);
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.model.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(exchangeName + DLQ_SUFFIX);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(queueName + DLQ_SUFFIX).build();
    }

    @Bean
    public Binding deadLetterbinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(routingKey + DLQ_SUFFIX);
    }

    @Bean
    public ScrapperQueueListener scrapperQueueListener(TrackerBot bot) {
        return new ScrapperQueueListener(bot);
    }
}
