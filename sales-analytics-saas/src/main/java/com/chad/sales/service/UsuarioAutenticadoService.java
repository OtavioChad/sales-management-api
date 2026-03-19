package com.chad.sales.service;

import org.springframework.stereotype.Service;

import com.chad.sales.config.AuthUtil;
import com.chad.sales.exception.UsuarioNotFoundException;
import com.chad.sales.model.Usuario;
import com.chad.sales.repository.UsuarioRepository;

@Service
public class UsuarioAutenticadoService {

    private final UsuarioRepository usuarioRepository;
    private final AuthUtil authUtil;

    public UsuarioAutenticadoService(UsuarioRepository usuarioRepository, AuthUtil authUtil) {
        this.usuarioRepository = usuarioRepository;
        this.authUtil = authUtil;
    }

    public Usuario get() {
        String email = authUtil.getEmailLogado();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
    }
}