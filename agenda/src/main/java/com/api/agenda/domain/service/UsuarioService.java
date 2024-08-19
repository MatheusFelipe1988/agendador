package com.api.agenda.domain.service;

import com.api.agenda.configuration.exception.BussinessException;
import com.api.agenda.domain.entity.Role;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.repository.IUsuaioService;
import com.api.agenda.domain.repository.RoleRepository;
import com.api.agenda.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuaioService {

    private final UsuarioRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario registerUser(Usuario usuario){
        if (repository.existsByEmail(usuario.getEmail())){
            throw new BussinessException(usuario.getEmail() + "already exists");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        System.out.println(usuario.getPassword());
        Role useRole = roleRepository.findByName("ROLE_USER").get();
        usuario.setRoles(Collections.singletonList(useRole));
        return repository.save(usuario);
    }

    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }

    @Transactional
    public void deleteUser(String email){
        Usuario theUsuario = getUser(email);
        if (theUsuario != null){
            repository.deleteByEmail(email);
        }
    }

    public Usuario getUser(String email){
        return repository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("Not found user"));

    }

}
