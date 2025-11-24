package com.Zipdrive.API.Service;
import com.Zipdrive.API.Model.UserProfile;
import com.Zipdrive.API.Response.MetadataResponse;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    public ResultResponse<UserProfile> createUserProfile(UserProfile userProfile) {
        UserProfile saved = userProfileRepository.save(userProfile);
        MetadataResponse metadata = new MetadataResponse();
        metadata.setCode("200");
        metadata.setMessage("Profile created successfully.");
        metadata.setNoOfRecords(1);
        return new ResultResponse<>(saved, metadata);
    }

    public ResultResponse<UserProfile> getUserProfileByEmail(String email) {
        Optional<UserProfile> profile = Optional.ofNullable(userProfileRepository.findByEmail(email));
        MetadataResponse metadata = new MetadataResponse();
        if (profile.isPresent()) {
            metadata.setCode("200");
            metadata.setMessage("Profile found.");
            metadata.setNoOfRecords(1);
            return new ResultResponse<>(profile.get(), metadata);
        } else {
            metadata.setCode("404");
            metadata.setMessage("Profile not found.");
            metadata.setNoOfRecords(0);
            return new ResultResponse<>(null, metadata);
        }
    }

    public ResultResponse<UserProfile> updateUserProfile(UserProfile updatedProfile) {
        MetadataResponse metadata = new MetadataResponse();
        UserProfile existing = userProfileRepository.findByEmail(updatedProfile.getEmail());
        if (existing == null) {
            metadata.setCode("404");
            metadata.setMessage("Profile not found.");
            metadata.setNoOfRecords(0);
            return new ResultResponse<>(null, metadata);
        }
        existing.setId(updatedProfile.getId());
        existing.setEmail(updatedProfile.getEmail());
        existing.setAddress(updatedProfile.getAddress());
        UserProfile saved = userProfileRepository.save(existing);
        metadata.setCode("200");
        metadata.setMessage("Profile updated successfully.");
        metadata.setNoOfRecords(1);
        return new ResultResponse<>(saved, metadata);
    }

    public ResultResponse<String> deleteUserProfile(String email) {
        MetadataResponse metadata = new MetadataResponse();
        UserProfile user = userProfileRepository.findByEmail(email);
        if (user != null) {
            userProfileRepository.delete(user);
            metadata.setCode("200");
            metadata.setMessage("Profile deleted.");
            metadata.setNoOfRecords(1);
            return new ResultResponse<>("Deleted: " + email, metadata);
        } else {
            metadata.setCode("404");
            metadata.setMessage("Profile not found.");
            metadata.setNoOfRecords(0);
            return new ResultResponse<>(null, metadata);
        }
    }

    public String uploadProfileImage(String email, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Uploaded file is empty or null");
        }
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (userProfile == null) {
            throw new IOException("User profile not found for email: " + email);
        }
        String rootPath = System.getProperty("user.dir");
        String uploadDirPath = rootPath + File.separator + "uploads" + File.separator + "profile";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Could not create upload directory");
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFilename = System.currentTimeMillis() + fileExtension;
        File destinationFile = new File(uploadDir, uniqueFilename);
        file.transferTo(destinationFile);
        String relativePath = "/uploads/profile/" + uniqueFilename;
        userProfile.setProfileImageUrl(relativePath);
        userProfileRepository.save(userProfile);
        return relativePath;
    }
}
