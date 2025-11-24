package com.Zipdrive.API.Controller;
import com.Zipdrive.API.Model.UserProfile;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.Service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/profile")
public class UserprofileController {
    @Autowired
    private UserProfileService userProfileService;
    @PostMapping("/create")
    public ResultResponse<UserProfile> createProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }
    @PostMapping("/get")
    public ResultResponse<UserProfile> getProfile(@RequestBody UserProfile request) {
        return userProfileService.getUserProfileByEmail(request.getEmail());
    }
    @PutMapping("/update")
    public ResultResponse<UserProfile> updateProfile(@RequestBody UserProfile updatedProfile) {
        return userProfileService.updateUserProfile(updatedProfile);
    }
    @DeleteMapping("/delete")
    public ResultResponse<String> deleteProfile(@RequestBody UserProfile request) {
        return userProfileService.deleteUserProfile(request.getEmail());
    }
    @PostMapping("/upload-image")
    public String uploadProfileImage(@RequestParam("email") String email,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        return userProfileService.uploadProfileImage(email, file);
    }
}
