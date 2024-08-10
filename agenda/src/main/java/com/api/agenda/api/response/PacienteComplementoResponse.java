package com.api.agenda.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteComplementoResponse {
    private Long id;
    private String nome;
    private String sobreNome;
    private String email;
    private String cpf;
    private List<EnderecoResponse> enderecos;
}
