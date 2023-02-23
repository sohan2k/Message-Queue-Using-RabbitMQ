package io.sohan.DemoRabbitMQ.repository;

import io.sohan.DemoRabbitMQ.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
