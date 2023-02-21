package io.sohan.DemoRabbitMQ.controller;

import io.sohan.DemoRabbitMQ.dto.SubscriptionDto;
import io.sohan.DemoRabbitMQ.service.SubscriptionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class RabbitBrokerController {
    private SubscriptionService subscriptionService;
    private final RabbitTemplate rabbitTemplate;

    public RabbitBrokerController(SubscriptionService subscriptionService, RabbitTemplate rabbitTemplate) {
        this.subscriptionService = subscriptionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/test")
    public String test(){
        return "hellop";
    }

    @PostMapping("/test")
    public SubscriptionDto add(@RequestBody SubscriptionDto dto){
        return subscriptionService.addSubscription(dto);
    }

    @PostMapping("/job")
    public ResponseEntity<String> createJob(@RequestBody SubscriptionDto subscriptionDto){
//        System.out.println(exchangeName+"key="+routingKey);
        rabbitTemplate.convertAndSend("direct-exchange","subscriptionQueue",subscriptionDto);
        return ResponseEntity.ok("Subscription request sent successfully");
    }
}
