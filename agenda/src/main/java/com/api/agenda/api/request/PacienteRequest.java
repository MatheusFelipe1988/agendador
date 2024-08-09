package com.api.agenda.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteRequest {
    private Long id;

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String nome;

    @NotBlank(message = "Seu sobre nome é obrigatório")
    private String sobreNome;

    @NotBlank(message = "Seu e-mail é obrigatório")
    private String email;

    @NotBlank(message = "Seu CPF é obrigatório")
    private String cpf;
}
