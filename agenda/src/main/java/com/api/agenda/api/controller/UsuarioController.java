package com.api.agenda.api.controller;

import com.api.agenda.api.request.UsuarioRequest;
import com.api.agenda.api.response.LoginTO;
import com.api.agenda.api.response.UsuarioResponse;
import com.api.agenda.configuration.token.TokenService;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public UsuarioController(UsuarioRepository repository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity register(@Valid @RequestBody UsuarioRequest request){
        if(this.repository.findByEmail(request.email()) != null){
            return ResponseEntity.badRequest().build();
        }

        String encryptPassword = new BCryptPasswordEncoder().encode(request.password());
        Usuario user = new Usuario(request.email(), encryptPassword, request.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UsuarioResponse response){
        var userPassword = new UsernamePasswordAuthenticationToken(response.email(), response.password());
        var auth = this.authenticationManager.authenticate(userPassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginTO(token));
    }
}
