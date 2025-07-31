package br.com.javaguides.springboot_rabbitmq_tutorial.controller;

import br.com.javaguides.springboot_rabbitmq_tutorial.dto.User;
import br.com.javaguides.springboot_rabbitmq_tutorial.publisher.RabbitMQJsonProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {

    private final RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        jsonProducer.sendJsonMessage(user);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Json message sent to RabbitMQ");
    }
}
