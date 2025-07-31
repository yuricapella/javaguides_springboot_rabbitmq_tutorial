package br.com.javaguides.stock_service.consumer;

import br.com.javaguides.stock_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.order.name}")
    public void consume(OrderEvent orderEvent){
        LOGGER.info("Order event received: {}", orderEvent.toString());

        // save order event data in database
    }

}
