package com.api.agenda.domain.repository;

import com.api.agenda.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String role);

    boolean existsByName(Role role);
}
