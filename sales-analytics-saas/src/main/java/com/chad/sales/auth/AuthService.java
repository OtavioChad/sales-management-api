package com.chad.sales.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chad.sales.dto.UsuarioLogadoDTO;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.UsuarioRepository;
import com.chad.sales.service.JwtService;

@Service
public class AuthService {
	
	private final JwtService jwtService;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
		
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		}

    public Usuario registrar(RegisterRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());

        usuario.setSenha(senhaCriptografada);
        
        if(usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        return usuarioRepository.save(usuario);
    }
    
    public String login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.gerarToken(usuario.getEmail());
    }
    
    public UsuarioLogadoDTO getUsuarioLogadoDTO() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioLogadoDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
    
    

}