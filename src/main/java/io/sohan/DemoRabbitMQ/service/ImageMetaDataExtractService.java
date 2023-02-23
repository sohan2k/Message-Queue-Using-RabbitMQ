package io.sohan.DemoRabbitMQ.service;

import io.sohan.DemoRabbitMQ.dto.ImageDto;
import io.sohan.DemoRabbitMQ.entity.Image;
import io.sohan.DemoRabbitMQ.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class ImageMetaDataExtractService extends Thread {
    private ImageRepository imageRepository;

    public ImageMetaDataExtractService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Value("${image.path}")
    private String path;
    public List<ImageDto> uploadImage(MultipartFile[] files) throws IOException {
        //Check and create path in local directory
        Path paths = Paths.get(path);
        if (!Files.isDirectory(paths)) {
            Files.createDirectory(paths);
        }
        List<ImageDto> responseDtoList = new ArrayList<>();
        int count = 0;
        //Check uploading file and save in directory
        for (MultipartFile file : files) {
//            paths = Paths.get(path + "/" + file.getOriginalFilename());
//            file.transferTo(paths);
            responseDtoList.add(multipartToImageDto(file));
            count += 1;
        }
        if (count == 0) {
            throw new InvalidObjectException("Please select image file");
        }
        return responseDtoList;
    }

    private ImageDto saveImage(MultipartFile file) {
        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .extension(Objects.requireNonNull(file.getContentType()).substring(6))
                .size((int) (file.getSize() / 1024))
                .build();

        image=imageRepository.save(image);
        ImageDto imageDto = imageToImageDto(image);
        return imageDto;
    }


    private ImageDto imageToImageDto(Image image){
        ImageDto dto=ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .extension(image.getExtension())
                .size(image.getSize())
                .build();
        return dto;
    }
    private Image imageDtoToImage(ImageDto imageDto){
        Image image=Image.builder()
                .id(imageDto.getId())
                .name(imageDto.getName())
                .extension(imageDto.getExtension())
                .size(imageDto.getSize())
                .build();
        return image;
    }

    private ImageDto multipartToImageDto(MultipartFile file) {
        return ImageDto.builder()
                .name(file.getOriginalFilename())
                .extension(Objects.requireNonNull(file.getContentType()).substring(6))
                .size((int) (file.getSize() / 1024))
                .build();
    }

    public void addImage(List<ImageDto> dtoList){
        dtoList.stream().map(this::imageDtoToImage).forEach(image -> imageRepository.save(image));
    }

}
