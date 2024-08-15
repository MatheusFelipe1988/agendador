package com.api.agenda.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobreNome;

    private String email;

    private String cpf;

    @OneToMany
    @JoinColumn(name = "paciente_id")
    private List<Endereco> endereco;

}
