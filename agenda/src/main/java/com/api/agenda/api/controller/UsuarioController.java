package com.api.agenda.api.controller;

import com.api.agenda.api.request.LoginRequest;
import com.api.agenda.api.response.UsuarioResponse;
import com.api.agenda.configuration.exception.UserErrorException;
import com.api.agenda.configuration.filter.AgendaUserDetails;
import com.api.agenda.configuration.token.TokenService;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario){
        try {
            service.registerUser(usuario);
            return ResponseEntity.ok("Register success");

        }catch (UserErrorException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenService.generateTokenForUser(authentication);
        AgendaUserDetails userDetails = (AgendaUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new UsuarioResponse(userDetails.getId(), userDetails.getEmail(), jwt, roles));
    }
}
