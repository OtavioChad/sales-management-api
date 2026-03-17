package com.chad.sales.auth;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.model.Usuario;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody RegisterRequest request) {

        return authService.registrar(request);

    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }

}