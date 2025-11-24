package com.Zipdrive.API.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UploadInsuranceRequest {
    private Long carId;
    private MultipartFile insuranceImage;
}

