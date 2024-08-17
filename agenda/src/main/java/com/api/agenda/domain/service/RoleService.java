package com.api.agenda.domain.service;

import com.api.agenda.configuration.exception.BussinessException;
import com.api.agenda.domain.entity.Role;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.repository.RoleRepository;
import com.api.agenda.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository repository;

    public List<Role> getRoles(){
        return repository.findAll();
    }

    public Role createRole(Role theRole){
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if(repository.existsByName(roleName)){
            throw new BussinessException(theRole.getName() + "Role already exist");
        }
        return repository.save(role);
    }

    public void deleteRole(Long roleId){
        this.removeAllUsersFromRole(roleId);
        repository.deleteById(roleId);
    }

    public Role findByName(String name){
        return repository.findByName(name).get();
    }

    public Usuario removeUserFromRole(Long userId, Long roleId){
        Optional<Usuario> usuario = usuarioRepository.findById(userId);
        Optional<Role> role = repository.findById(roleId);
        if(role.isPresent() && role.get().getUsuarios().contains(usuario.get())){
            role.get().removeUserFromRole(usuario.get());
            repository.save(role.get());
            return usuario.get();
        }
        throw new BussinessException("Usuario not found");
    }

    public Usuario assignToRoleUser(Long userId, Long roleId){
        Optional<Usuario> usuario = usuarioRepository.findById(userId);
        Optional<Role> role = repository.findById(roleId);
        if(usuario.isPresent() && usuario.get().getRoles().contains(role.get())){
            throw new BussinessException(usuario.get().getNome() + "is already assigned" + role.get().getName() + "role");
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(usuario.get());
            repository.save(role.get());
        }
        return usuario.get();
    }

    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = repository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return repository.save(role.get());
    }
}
