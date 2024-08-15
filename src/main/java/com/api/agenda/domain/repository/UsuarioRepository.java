package com.api.agenda.domain.repository;

import com.api.agenda.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    UserDetails findByEmail(String email);
}
