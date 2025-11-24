package com.Zipdrive.API.Service;
import com.Zipdrive.API.Model.User;
import com.Zipdrive.API.Response.MetadataResponse;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public ResultResponse<User> saveUser(User user) {
        ResultResponse<User> response = new ResultResponse<>();
        MetadataResponse metadata = new MetadataResponse();
        user.setEmail(user.getEmail().toLowerCase());
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmailIgnoreCase(user.getEmail()));
        if (existingUser.isPresent()) {
            metadata.setCode("400K");
            metadata.setMessage("User already registered.");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else {
            User savedUser = userRepository.save(user);
            metadata.setCode("200K");
            metadata.setMessage("User registered successfully.");
            metadata.setNoOfRecords(1);
            response.setResult(savedUser);
        }
        response.setMetadata(metadata);
        return response;
    }

    public ResultResponse<User> loginUser(String email, String password) {
        ResultResponse<User> response = new ResultResponse<>();
        MetadataResponse metadata = new MetadataResponse();
        User user = userRepository.findByEmailIgnoreCase(email.toLowerCase());
        if (user == null) {
            metadata.setCode("404K");
            metadata.setMessage("User not found.");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else if (!user.getPassword().equals(password)) {
            metadata.setCode("401K");
            metadata.setMessage("Invalid password.");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else {
            metadata.setCode("200K");
            metadata.setMessage("Login successful.");
            metadata.setNoOfRecords(1);
            response.setResult(user);
        }
        response.setMetadata(metadata);
        return response;
    }

    public ResultResponse<String> sendOtp(String email) {
        ResultResponse<String> response = new ResultResponse<>();
        MetadataResponse metadata = new MetadataResponse();
        User user = userRepository.findByEmailIgnoreCase(email.toLowerCase());
        if (user == null) {
            metadata.setCode("404K");
            metadata.setMessage("Email not registered");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else {
            String otp = String.valueOf((int) (Math.random() * 9000) + 1000);
            user.setOtp(otp);
            userRepository.save(user);
            emailService.sendOtpEmail(user.getEmail(), otp);
            metadata.setCode("200K");
            metadata.setMessage("OTP sent to email.");
            metadata.setNoOfRecords(1);
            response.setResult("OTP sent successfully");
        }
        response.setMetadata(metadata);
        return response;
    }

    public ResultResponse<String> verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        ResultResponse<String> response = new ResultResponse<>();
        MetadataResponse metadata = new MetadataResponse();
        User user = userRepository.findByEmailIgnoreCase(email.toLowerCase());
        if (user == null) {
            metadata.setCode("404K");
            metadata.setMessage("User not found");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else if (!otp.equals(user.getOtp())) {
            metadata.setCode("401K");
            metadata.setMessage("Invalid OTP");
            metadata.setNoOfRecords(0);
            response.setResult(null);
        } else {
            user.setPassword(newPassword);
            user.setOtp(null);
            userRepository.save(user);
            metadata.setCode("200K");
            metadata.setMessage("Password reset is successful !");
            metadata.setNoOfRecords(1);
            response.setResult("Password has been updated !");
        }
        response.setMetadata(metadata);
        return response;
    }
}
