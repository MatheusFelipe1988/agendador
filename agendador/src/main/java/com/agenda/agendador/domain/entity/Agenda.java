package com.agenda.agendador.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "agenda")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_hora")
    private LocalDateTime horario;

    @ManyToOne
    private Paciente paciente;
}
