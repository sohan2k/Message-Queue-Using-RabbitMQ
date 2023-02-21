package io.sohan.DemoRabbitMQ.service;

import io.sohan.DemoRabbitMQ.dto.SubscriptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQListnerService {
    public RabbitMQListnerService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    private SubscriptionService subscriptionService;

    @RabbitListener(queues = {"subscriptionQueue"})
    public void onSubscribe(SubscriptionDto dto){
        System.out.println("in consumer");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        log.info("Subscription Register Event Received: {}", dto);
        subscriptionService.addSubscription(dto);
    }
}
