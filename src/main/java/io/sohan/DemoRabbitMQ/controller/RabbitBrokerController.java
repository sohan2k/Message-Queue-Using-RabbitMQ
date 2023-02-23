package io.sohan.DemoRabbitMQ.controller;

import io.sohan.DemoRabbitMQ.dto.ImageDto;
import io.sohan.DemoRabbitMQ.dto.SubscriptionDto;
import io.sohan.DemoRabbitMQ.service.ImageMetaDataExtractService;
import io.sohan.DemoRabbitMQ.service.SubscriptionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RabbitBrokerController {
    private SubscriptionService subscriptionService;
    private final RabbitTemplate rabbitTemplate;

    private ImageMetaDataExtractService imageMetaDataExtractService;

    public RabbitBrokerController(SubscriptionService subscriptionService, RabbitTemplate rabbitTemplate, ImageMetaDataExtractService imageMetaDataExtractService) {
        this.subscriptionService = subscriptionService;
        this.rabbitTemplate = rabbitTemplate;
        this.imageMetaDataExtractService = imageMetaDataExtractService;
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
        rabbitTemplate.convertAndSend("direct-exchange","subscriptionQueue",subscriptionDto);
        return ResponseEntity.ok("Subscription request sent successfully");
    }

    @PostMapping("/image/upload")
    public ResponseEntity<String> addImage(@RequestParam("file") MultipartFile[] files)throws IOException {
        rabbitTemplate.convertAndSend("direct-exchange","imageQueue",imageMetaDataExtractService.uploadImage(files));
        return ResponseEntity.ok("Image MetaData store request sent successfully");
    }

}
