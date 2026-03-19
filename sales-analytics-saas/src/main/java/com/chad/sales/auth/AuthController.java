package com.chad.sales.auth;

import org.springframework.web.bind.annotation.*;

import com.chad.sales.dto.UsuarioLogadoDTO;
import com.chad.sales.dto.UsuarioResponseDTO;
import com.chad.sales.model.Usuario;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UsuarioResponseDTO register(@RequestBody RegisterRequest request) {
        return new UsuarioResponseDTO(authService.registrar(request));
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }
    
    @GetMapping("/me")
    public UsuarioLogadoDTO usuarioLogado() {
        return authService.getUsuarioLogadoDTO();
    }

}