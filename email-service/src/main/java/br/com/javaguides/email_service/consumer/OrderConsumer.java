package br.com.javaguides.email_service.consumer;

import br.com.javaguides.email_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER =  LoggerFactory.getLogger(OrderConsumer.class);

    //sempre que cadastra o consumer, para funcionar primeiro deve-se enviar um post do producer para criar a conexão,
    // criar a fila nova também, depois de tudo criado, ao rodar a aplicação do email, vai consumir o json enviado.
    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consume(OrderEvent orderEvent) {
        LOGGER.info("Order event received from RabbitMQ: {}", orderEvent.toString());

    }
}
