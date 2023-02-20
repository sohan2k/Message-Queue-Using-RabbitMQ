package io.sohan.DemoRabbitMQ.service;

import io.sohan.DemoRabbitMQ.dto.SubscriptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    public SubscriptionDto addSubscription(SubscriptionDto dto){
        return dto;
    }
}
