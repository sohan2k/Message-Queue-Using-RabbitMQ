package io.sohan.DemoRabbitMQ.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
//    @Bean
    public Queue createSubscription(){
        return new Queue("subscriptionQueue",false);
    }

    @Bean
    public Queue uploadImage(){
        return new Queue("imageQueue",false);
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange("direct-exchange");
    }

//    @Bean
//    Binding subscriptionBinding(Queue subscriptionQueue, DirectExchange exchange){
//        return BindingBuilder.bind(subscriptionQueue).to(exchange).with("subscriptionQueue");
//    }

    @Bean
    Binding ImageBinding(Queue imageQueue, DirectExchange exchange){
        return BindingBuilder.bind(imageQueue).to(exchange).with("imageQueue");
    }
}
