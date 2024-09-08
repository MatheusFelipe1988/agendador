package com.api.agenda.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<Usuario> usuarios = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void assignRoleToUser(Usuario usuario){
        usuario.getRoles().add(this);
        this.getUsuarios().add(usuario);
    }

    public void removeUserFromRole(Usuario usuario){
        usuario.getRoles().add(this);
        this.getUsuarios().add(usuario);
    }

    public void removeAllUsersFromRole(){
        if(this.getUsuarios() != null){
            List<Usuario> roleUsers = this.getUsuarios().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);
        }
    }

    public  String getName(){
        return name != null? name : "";
    }


}
