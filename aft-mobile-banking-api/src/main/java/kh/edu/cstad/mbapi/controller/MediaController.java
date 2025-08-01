package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.MediaResponse;
import kh.edu.cstad.mbapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

}
