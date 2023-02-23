package io.sohan.DemoRabbitMQ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto implements Serializable {
    private Long id;
    private String name;
    private Integer size;
    private String extension;

}
