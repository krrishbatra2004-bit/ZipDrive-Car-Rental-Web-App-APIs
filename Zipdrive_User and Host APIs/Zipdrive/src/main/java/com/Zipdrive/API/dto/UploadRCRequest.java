package com.Zipdrive.API.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UploadRCRequest {
    private Long carId;
    private MultipartFile rcImageFront;
    private MultipartFile rcImageBack;
}

