package com.api.agenda.api.request;

import com.api.agenda.domain.entity.Role;

public record UsuarioRequest(String email, String senha, Role role) {
}
