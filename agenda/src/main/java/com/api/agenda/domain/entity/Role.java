package com.api.agenda.domain.entity;

public enum Role {
    ADMIN("admin"),
    USER("user");

    private String role;

    public String getRole() {
        return role;
    }

    Role(String role){
        this.role= role;
    }
}
