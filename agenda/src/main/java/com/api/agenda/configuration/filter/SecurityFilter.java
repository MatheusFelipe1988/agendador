package com.api.agenda.configuration.filter;

import com.api.agenda.configuration.token.TokenService;
import com.api.agenda.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService service;
    private final UsuarioRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null){
            var subject = service.validateToken(token.toString());
            UserDetails user = repository.findByEmail(subject);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Object recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null) return null;
        return authHeader.replace("Bearer ","");
    }
}