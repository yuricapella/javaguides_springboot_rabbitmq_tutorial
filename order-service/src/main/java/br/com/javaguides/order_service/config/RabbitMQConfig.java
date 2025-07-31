package br.com.javaguides.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;

    @Value("${rabbitmq.exchange.order.name}")
    private String exchange;

    @Value("${rabbitmq.binding.order.routing.key}")
    private String orderRountingKey;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRountingKey;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRountingKey);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRountingKey);
    }



    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
