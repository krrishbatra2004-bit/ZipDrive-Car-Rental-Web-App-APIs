package com.Zipdrive.API.dto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Data
public class UploadCarImagesRequest {
    private Long carId;
    private List<MultipartFile> carImages;
}
