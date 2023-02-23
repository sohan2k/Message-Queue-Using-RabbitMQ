package io.sohan.DemoRabbitMQ.service;

import io.sohan.DemoRabbitMQ.dto.ImageDto;
import io.sohan.DemoRabbitMQ.dto.SubscriptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class RabbitMQListnerService {
    private ImageMetaDataExtractService imageMetaDataExtractService;
    public RabbitMQListnerService(ImageMetaDataExtractService imageMetaDataExtractService, SubscriptionService subscriptionService) {
        this.imageMetaDataExtractService = imageMetaDataExtractService;
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


    @RabbitListener(queues = {"imageQueue"})
    public void metaDataExtract(List<ImageDto> dtoList) throws InterruptedException {
        Thread.sleep(10000);
//        imageMetaDataExtractService.addImage(dtoList);
        ExecutorService executorService=Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    imageMetaDataExtractService.addImage(dtoList);
                    System.out.println(Thread.currentThread().getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
