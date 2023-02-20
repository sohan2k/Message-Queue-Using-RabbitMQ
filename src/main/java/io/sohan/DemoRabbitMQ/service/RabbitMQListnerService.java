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

    @RabbitListener(queues = {"q.sub-register"})
    public void onSubscribe(SubscriptionDto dto){
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
        log.info("Subscription Register Event Received: {}", dto);
        subscriptionService.addSubscription(dto);
    }
}
