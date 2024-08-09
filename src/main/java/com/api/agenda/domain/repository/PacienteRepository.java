package com.api.agenda.domain.repository;

import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.entity.PacienteAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String  cpf);

    @Query("SELECT p FROM Paciente p WHERE p.cpf = ?1")
    Optional<Paciente> getByCpf(String cpf);

    @Query(value = "SELECT * FROM Paciente p WHERE p.cpf = ?1", nativeQuery = true)
    Optional<Paciente> getByCpfNative(String cpf);

    @Query(value = "select p.nome, p.cpf, a.horario, a.descricao from paciente p, agenda a where p.id = a.paciente_id", nativeQuery = true)
    List<PacienteAgenda> findAllAgenScheduling();
}
