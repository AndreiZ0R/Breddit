package com.andreiz0r.breddit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    @Value("#{${images.path}}")
    private String imagesPath;

    public Optional<byte[]> findImage(final String resourceName) {
        try {
            return Optional.of(Files.readAllBytes(Paths.get(imagesPath, "/profile/Breddit.png")));
        } catch (IOException e) {
            log.error("Resource with name {} not found.", resourceName);
        }

        return Optional.empty();
    }

    @Async
    public CompletableFuture<String> saveImage(final MultipartFile image) {
        File compressedImage = new File(imagesPath + "/profile/Test_compressed.jpg");

        try {
            Thumbnails.of(image.getInputStream())
                    .scale(0.5)
                    .outputQuality(0.2)
                    .toFile(compressedImage);
            return CompletableFuture.completedFuture(compressedImage.getName());
        } catch (IOException ex) {
            log.error("Could not save resource to path /profile");
        }

        return CompletableFuture.completedFuture("");
    }

}
