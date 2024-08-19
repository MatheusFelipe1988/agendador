package com.api.agenda.configuration.filter;

import com.api.agenda.configuration.exception.BussinessException;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.findByEmail(email)
            .orElseThrow(() -> new BussinessException("Not Found"));

        return AgendaUserDetails.buildUserDetails(usuario);
    }
}
