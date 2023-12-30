package com.agenda.agendador.domain.service;

import com.agenda.agendador.domain.entity.Agenda;
import com.agenda.agendador.domain.entity.Paciente;
import com.agenda.agendador.domain.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class AgendaService {
    private final AgendaRepository repository;
    private final PacienteService pacienteService;

    public List<Agenda> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agenda> BuscaPorId(Long id) {
        return repository.findById(id);
    }

    public Agenda salvar(Agenda agenda) {
        Optional<Paciente> optPaciente = pacienteService.buscarPorId(agenda.getPaciente().getId());
        if (optPaciente.isEmpty()) {
            throw new BusinessException("Nao encontrado");
        }
        Optional<Agenda> optHorario = repository.findByHorario(agenda.getHorario());

        if (optHorario.isPresent()) {
            throw new BusinessException("Agendamento já registrado");
        }
        agenda.setPaciente(optPaciente.get());
        agenda.setDataCriacao(LocalDateTime.now());

        return repository.save(agenda);
    }
}
