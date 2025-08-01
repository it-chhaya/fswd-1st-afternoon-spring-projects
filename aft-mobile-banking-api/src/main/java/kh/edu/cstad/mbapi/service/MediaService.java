package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaResponse upload(MultipartFile file);

}
