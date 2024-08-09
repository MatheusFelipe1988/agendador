package com.api.agenda.domain.entity;


import java.time.LocalDateTime;

public interface PacienteAgenda {
    String getNome();
    String getCpf();
    LocalDateTime getHorario();
    String getDescricao();
}
