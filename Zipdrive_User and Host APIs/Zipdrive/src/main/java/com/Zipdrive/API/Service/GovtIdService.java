package com.Zipdrive.API.Service;
import com.Zipdrive.API.Model.GovernmentId;
import com.Zipdrive.API.Model.User;
import com.Zipdrive.API.Response.MetadataResponse;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.dto.UploadGovernmentIdRequest;
import com.Zipdrive.API.repository.GovernmentIdRepository;
import com.Zipdrive.API.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class GovtIdService {
    private final GovernmentIdRepository governmentIdRepository;
    private final UserRepository userRepository;
    @Value("${upload.dir}")
    private String uploadDir;
    @Transactional
    public ResultResponse<String> uploadGovernmentId(long userId, UploadGovernmentIdRequest request, MultipartFile file) {
        // 1. Check if user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return new ResultResponse<>("User not found", MetadataResponse.internalServerError());
        }
        // 2. Validate file
        if (file == null || file.isEmpty()) {
            return new ResultResponse<>("Uploaded file is empty", MetadataResponse.badRequest());
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return new ResultResponse<>("Original file name is missing", MetadataResponse.badRequest());
        }
        // 3. Generate unique file name with extension
        String fileExtension = originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFileName = UUID.randomUUID() + fileExtension;
        // 4. Create path for saving the file
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        try {
            // Ensure the upload directory exists
            Files.createDirectories(filePath.getParent());
            // Save the file
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            return new ResultResponse<>("Failed to save file: " + e.getMessage(),MetadataResponse.internalServerError());
        }
        // 5. Create and save GovernmentId entity
        GovernmentId governmentId = GovernmentId.builder()
                .idType(request.getIdType())
                .idNumber(request.getIdNumber())
                .filePath(filePath.toString())
                .verified(false)
                .user(optionalUser.get())
                .build();
        governmentIdRepository.save(governmentId);
        return new ResultResponse<>("Government ID uploaded successfully",MetadataResponse.success());
    }
}
