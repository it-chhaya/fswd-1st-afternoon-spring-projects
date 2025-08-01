package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Media;
import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.repository.MediaRepository;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse upload(MultipartFile file) {

        // 1. Save file to server path
        // a.bc.png
        String name = UUID.randomUUID().toString();
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename())
                .lastIndexOf(".");
        String extension = file.getOriginalFilename()
                .substring(lastIndex + 1);
        // Create path object
        Path path = Paths.get(serverPath + String.format("%s.%s",
                name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed");
        }

        // 2. Save metadata of media into database
        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .extension(media.getExtension())
                .mimeTypeFile(media.getMimeTypeFile())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .size(file.getSize())
                .build();
    }

}
