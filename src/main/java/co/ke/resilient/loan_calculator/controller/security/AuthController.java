package co.ke.resilient.loan_calculator.controller.security;

import co.ke.resilient.loan_calculator.model.security.RoleGroupEnum;
import co.ke.resilient.loan_calculator.model.security.User;
import co.ke.resilient.loan_calculator.payload.security.*;
import co.ke.resilient.loan_calculator.service.security.AuthService;
import co.ke.resilient.loan_calculator.service.security.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public record AuthController(AuthService authService, UserService userService) {
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest.getUsername(), authRequest.getPassword());
    }

    @PostMapping("set-password")
    public EmailVerificationResponse setPassword(@RequestBody PasswordRequest request) {
        return userService.setPassword(request.getToken(), request.getPassword());
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody UserRequest request) {
        userService.createUser(request, RoleGroupEnum.USER);
    }
}