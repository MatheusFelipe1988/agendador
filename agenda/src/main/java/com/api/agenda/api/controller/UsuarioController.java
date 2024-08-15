package com.api.agenda.api.controller;

import com.api.agenda.api.request.UsuarioRequest;
import com.api.agenda.api.response.LoginTO;
import com.api.agenda.api.response.UsuarioResponse;
import com.api.agenda.configuration.token.TokenService;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> register(@Validated @RequestBody UsuarioRequest request){
        if(this.repository.findByEmail(request.email()) != null){
            return ResponseEntity.badRequest().build();
        }

        String encryptPassword = new BCryptPasswordEncoder().encode(request.senha());
        Usuario user = new Usuario(request.email(), encryptPassword, request.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UsuarioResponse response){
        var userPassword = new UsernamePasswordAuthenticationToken(response.email(), response.senha());
        var auth = this.authenticationManager.authenticate(userPassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginTO(token));
    }
}
