package com.Zipdrive.API.Controller;

import com.Zipdrive.API.Model.User;
import com.Zipdrive.API.Response.ResultResponse;
import com.Zipdrive.API.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResultResponse<User> registerUser(@RequestBody User user) {
        user.setEmail(user.getEmail().toLowerCase());
        return userService.saveUser(user);
    }
    @PostMapping("/login")
    public ResultResponse<User> loginUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email").toLowerCase();
        String password = payload.get("password");
        return userService.loginUser(email, password);
    }
    @PostMapping("/send-otp")
    public ResultResponse<String> sendOtp(@RequestBody Map<String, String> payload) {
        String email = payload.get("email").toLowerCase();
        return userService.sendOtp(email);
    }
    @PostMapping("/reset-password")
    public ResultResponse <String> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email").toLowerCase();
        String otp = payload.get("otp");
        String newPassword = payload.get("newPassword");
        return userService.verifyOtpAndResetPassword(email, otp, newPassword);
    }

}
