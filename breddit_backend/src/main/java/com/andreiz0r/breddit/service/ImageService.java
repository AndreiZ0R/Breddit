package com.andreiz0r.breddit.service;

import com.andreiz0r.breddit.utils.AppUtils.Images;
import com.andreiz0r.breddit.utils.BytesFormatter;
import com.andreiz0r.breddit.utils.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.andreiz0r.breddit.utils.AppUtils.KILOBYTE;
import static com.andreiz0r.breddit.utils.AppUtils.MEGABYTE;
import static com.andreiz0r.breddit.utils.AppUtils.POST_IMAGE_PATH;
import static com.andreiz0r.breddit.utils.AppUtils.PROFILE_IMAGE_PATH;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    @Value("#{${images.path}}")
    private String imagesPath;
    private final PostService postService;

    private final static int MINIMUM_JPG_VALUE = KILOBYTE;
    private final static float MAXIMUM_JPG_VALUE = 13.5f * MEGABYTE;


    public Optional<byte[]> findImage(final String resourceName) {
        try {
            return Optional.of(Files.readAllBytes(Paths.get(imagesPath, resourceName)));
        } catch (IOException e) {
            log.error("Resource with name {} not found.", resourceName);
        }

        return Optional.empty();
    }

    public Optional<byte[]> findImage(final String resourceName, final ImageType imageType) {
        try {
            return Optional.of(Files.readAllBytes(Paths.get(getFilePath(resourceName, imageType))));
        } catch (IOException e) {
            log.error("Resource with name {} not found.", resourceName);
        }

        return Optional.empty();
    }

    public Optional<List<byte[]>> findImages(final Integer postId) {
        return postService.getPostImagesUrl(postId)
                .map(imagesPath ->
                             imagesPath.stream()
                                     .map(path -> findImage(path, ImageType.POST).orElse(null))
                                     .filter(Objects::nonNull)
                                     .collect(Collectors.toList())
                );
    }

    @SneakyThrows
    @Async
    public CompletableFuture<Optional<String>> saveImage(final MultipartFile image, final String identifier, final ImageType imageType) {
        long initialSize = image.getSize();

        log.info("Starting picture upload...");
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        BufferedImage compressedBI = Scalr.resize(bufferedImage,
                                                  Scalr.Method.BALANCED,
                                                  Scalr.Mode.FIT_EXACT,
                                                  Images.DEFAULT_WIDTH,
                                                  Images.DEFAULT_HEIGHT);
        File outputImage = new File(getFilePath(identifier, imageType));
        ImageIO.write(compressedBI, Images.FORMAT_JPG, outputImage);

        log.info("Done uploading picture to path: {}, initial size: {}, upload size: {}",
                 outputImage.getPath().replace(imagesPath, ""),
                 BytesFormatter.toKilobytesString(initialSize),
                 BytesFormatter.toKilobytesString(outputImage.length()));

        return CompletableFuture.completedFuture(Optional.of(outputImage.getName()));
    }

    private float interpolateImageQuality(final long imageSize) {
        return 1 - ((imageSize - MINIMUM_JPG_VALUE) / (MAXIMUM_JPG_VALUE - MINIMUM_JPG_VALUE));
    }

    private String getFilePath(final String identifier, final ImageType imageType) {
        return switch (imageType) {
            case PROFILE -> imagesPath + PROFILE_IMAGE_PATH + identifier + ".jpg";
            case POST -> imagesPath + POST_IMAGE_PATH + identifier + ".jpg";
        };
    }

    private byte[] compressImage(MultipartFile mpFile, float quality) {
//        float quality = 0.3f;
        String imageName = mpFile.getOriginalFilename();
        String imageExtension = "jpg";
        // Returns an Iterator containing all currently registered ImageWriters that claim to be able to encode the named format.
        // You don't have to register one yourself; some are provided.
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(imageExtension).next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // Check the api value that suites your needs.
        // A compression quality setting of 0.0 is most generically interpreted as "high compression is important,"
        // while a setting of 1.0 is most generically interpreted as "high image quality is important."
        imageWriteParam.setCompressionQuality(quality);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // MemoryCacheImageOutputStream: An implementation of ImageOutputStream that writes its output to a regular
        // OutputStream, i.e. the ByteArrayOutputStream.
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(baos);
        // Sets the destination to the given ImageOutputStream or other Object.
        imageWriter.setOutput(imageOutputStream);
        BufferedImage originalImage = null;
        try (InputStream inputStream = mpFile.getInputStream()) {
            originalImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            String info = String.format("compressImage - bufferedImage (file %s)- IOException - message: %s ", imageName, e.getMessage());
            log.error(info);
            return baos.toByteArray();
        }
        IIOImage image = new IIOImage(originalImage, null, null);
        try {
            imageWriter.write(null, image, imageWriteParam);
        } catch (IOException e) {
            String info = String.format("compressImage - imageWriter (file %s)- IOException - message: %s ", imageName, e.getMessage());
            log.error(info);
        } finally {
            imageWriter.dispose();
        }
        return baos.toByteArray();
    }
}
