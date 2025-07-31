package br.com.javaguides.springboot_rabbitmq_tutorial.consumer;

import br.com.javaguides.springboot_rabbitmq_tutorial.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    // Toda vez que tiver uma mensagem nessa queue parametrizada do application.properties, vai chamar o metodo consume.
    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(User user){
        LOGGER.info("Received message -> {}", user.toString());
    }
}
