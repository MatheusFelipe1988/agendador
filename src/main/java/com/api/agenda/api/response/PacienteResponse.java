package com.api.agenda.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteResponse {
    private Long id;
    private String nome;
    private String sobreNome;
    private String email;
    private String cpf;

}
