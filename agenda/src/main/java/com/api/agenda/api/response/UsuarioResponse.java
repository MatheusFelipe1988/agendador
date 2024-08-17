package com.api.agenda.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String token;
    private String email;
    private String type = "Bearer";
    private List<String> roles;

    public UsuarioResponse(Long id, String token, String email, List<String> roles) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.roles = roles;
    }
}
