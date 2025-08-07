package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.RegisterRequest;
import kh.edu.cstad.mbapi.dto.RegisterResponse;
import kh.edu.cstad.mbapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

}
