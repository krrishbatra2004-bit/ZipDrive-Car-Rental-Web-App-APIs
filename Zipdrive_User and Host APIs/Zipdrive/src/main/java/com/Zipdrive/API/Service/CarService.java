package com.Zipdrive.API.Service;
import com.Zipdrive.API.Model.Car;
import com.Zipdrive.API.Response.MetadataResponse;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.dto.AddCarRequest;
import com.Zipdrive.API.dto.UploadRCRequest;
import com.Zipdrive.API.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    @Value("${file.upload.rc-dir:uploads/rc}")
    private String rcUploadDir;
    @Value("${upload.dir:C:/MyAppUploads}")
    private String rootUploadDir;
    @Transactional

    public ResultResponse<Long> addCar(AddCarRequest request) {
        try {
            Car car = Car.builder()
                    .make(request.getMake())
                    .model(request.getModel())
                    .yearOfMake(request.getYearOfMake())
                    .kmsDriven(request.getKmsDriven())
                    .rcNumber(request.getRcNumber())
                    .build();
            car = carRepository.save(car);
            return new ResultResponse<>(car.getId(), MetadataResponse.success());
        } catch (Exception e) {
            return new ResultResponse<>(0L, MetadataResponse.internalServerError());
        }
    }

    @Transactional
    public ResultResponse<String> uploadRC(UploadRCRequest request) {
        Optional<Car> optionalCar = carRepository.findById(request.getCarId());
        if (optionalCar.isEmpty()) {
            return new ResultResponse<>("Car not found", MetadataResponse.badRequest());
        }
        MultipartFile front = request.getRcImageFront();
        MultipartFile back = request.getRcImageBack();
        if (front.isEmpty() || back.isEmpty()) {
            return new ResultResponse<>("Both front and back images are required", MetadataResponse.badRequest());
        }
        try {
            String frontPath = saveFile(front, rcUploadDir);
            String backPath = saveFile(back, rcUploadDir);
            Car car = optionalCar.get();
            car.setRcImageFront(frontPath);
            car.setRcImageBack(backPath);
            carRepository.save(car);
            return new ResultResponse<>("RC images uploaded successfully", MetadataResponse.success());
        } catch (IOException e) {
            return new ResultResponse<>("Failed to upload RC images", MetadataResponse.internalServerError());
        }
    }

    @Transactional
    public ResultResponse<String> addInsurance(Long carId, MultipartFile insuranceImage) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            return new ResultResponse<>("Car not found", MetadataResponse.internalServerError());
        }
        if (insuranceImage == null || insuranceImage.isEmpty()) {
            return new ResultResponse<>("Uploaded file is empty", MetadataResponse.badRequest());
        }
        String originalFilename = insuranceImage.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return new ResultResponse<>("Original file name is missing", MetadataResponse.badRequest());
        }
        String fileExtension = originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFileName = UUID.randomUUID() + fileExtension;
        Path filePath = Paths.get(rootUploadDir, "insurance", uniqueFileName);
        try {
            Files.createDirectories(filePath.getParent());
            insuranceImage.transferTo(filePath.toFile());
        } catch (IOException e) {
            return new ResultResponse<>("Failed to save file: " + e.getMessage(), MetadataResponse.internalServerError());
        }
        Car car = optionalCar.get();
        car.setInsuranceImagePath(filePath.toString());
        carRepository.save(car);
        return new ResultResponse<>("Insurance image uploaded successfully", MetadataResponse.success());
    }

    @Transactional
    public ResultResponse<String> uploadCarImages(Long carId, List<MultipartFile> carImages) {
        if (carImages == null || carImages.isEmpty()) {
            return new ResultResponse<>("No car images provided", MetadataResponse.badRequest());
        }
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            return new ResultResponse<>("Car not found", MetadataResponse.badRequest());
        }
        List<String> savedPaths =new ArrayList<>();
        try {
            for (MultipartFile file : carImages) {
                if (!file.isEmpty()) {
                    String imagePath = saveFile(file, Paths.get(rootUploadDir, "carImages").toString());
                    savedPaths.add(imagePath);
                }
            }
            Car car = optionalCar.get();
            car.setCarImagePaths(savedPaths);
            carRepository.save(car);
            return new ResultResponse<>("Car images uploaded successfully", MetadataResponse.success());
        } catch (IOException e) {
            return new ResultResponse<>("Failed to upload car images: " + e.getMessage(), MetadataResponse.internalServerError());
        }
    }

    @Transactional
    public ResultResponse<String> uploadFastTag(Long carId, MultipartFile fasttagImage) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            return new ResultResponse<>("Car not found", MetadataResponse.internalServerError());
        }
        if (fasttagImage == null || fasttagImage.isEmpty()) {
            return new ResultResponse<>("Uploaded FastTAG image is empty", MetadataResponse.badRequest());
        }
        try {
            String fasttagPath = saveFile(fasttagImage, "fasttag");
            Car car = optionalCar.get();
            car.setFasttagImagePath(fasttagPath);
            carRepository.save(car);
            return new ResultResponse<>("FastTAG image uploaded successfully", MetadataResponse.success());
        } catch (IOException e) {
            return new ResultResponse<>("Failed to save FastTAG file: " + e.getMessage(), MetadataResponse.internalServerError());
        }
    }

    private String saveFile(MultipartFile file, String baseDir) throws IOException {
        String extension = file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")
                ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
                : "";
        String uniqueFileName = UUID.randomUUID() + extension;
        Path savePath = Paths.get(baseDir).toAbsolutePath().normalize();
        Files.createDirectories(savePath);
        Path fullFilePath = savePath.resolve(uniqueFileName);
        file.transferTo(fullFilePath.toFile());
        return fullFilePath.toString();
    }
}
