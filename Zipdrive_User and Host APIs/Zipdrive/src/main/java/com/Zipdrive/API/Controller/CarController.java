package com.Zipdrive.API.Controller;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.Service.CarService;
import com.Zipdrive.API.dto.AddCarRequest;
import com.Zipdrive.API.dto.UploadRCRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/add")
    public ResultResponse<Long> addCar(@RequestBody AddCarRequest request) {
        return carService.addCar(request);
    }

    @PostMapping(value = "/upload-rc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<String> uploadRC(
            @RequestParam("carId") Long carId,
            @RequestParam("rcImageFront") MultipartFile rcImageFront,
            @RequestParam("rcImageBack") MultipartFile rcImageBack) {
        UploadRCRequest request = new UploadRCRequest();
        request.setCarId(carId);
        request.setRcImageFront(rcImageFront);
        request.setRcImageBack(rcImageBack);
        return carService.uploadRC(request);
    }
    @PostMapping("/upload-Insurance")
    public ResultResponse<String> uploadInsurance(
            @RequestParam("carId") Long carId,
            @RequestParam("insuranceImage") MultipartFile insuranceImage) {
        return carService.addInsurance(carId, insuranceImage);
    }
    @PostMapping("/upload-carimages")
    public ResponseEntity<ResultResponse<String>> uploadCarImages(
            @RequestParam("carId") Long carId,
            @RequestParam("carImages") List<MultipartFile> carImages
    ) {
        return ResponseEntity.ok(carService.uploadCarImages(carId, carImages));
    }
    @PostMapping("/upload-fasttag")
    public ResultResponse<String> uploadFastTag( @RequestParam("carId") Long carId,
                                                 @RequestParam("fasttagImage") MultipartFile fasttagImage) {
        return carService.uploadFastTag(carId, fasttagImage);
    }
}
