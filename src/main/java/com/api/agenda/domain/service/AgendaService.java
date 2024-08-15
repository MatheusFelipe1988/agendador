package com.api.agenda.domain.service;

import com.api.agenda.domain.entity.Agenda;
import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.repository.AgendaRepository;
import com.api.agenda.configuration.exception.BussinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendaService {

    private final AgendaRepository repository;
    private final PacienteService service;

    public List<Agenda> listerAll(){
        return repository.findAll();
    }

    public Optional<Agenda> getById(Long id){
        return repository.findById(id);
    }

    public Agenda saver(Agenda agenda){
        Optional<Paciente> optPaciente = service.getForId(agenda.getPaciente().getId());

        if (optPaciente.isEmpty()){
            throw new BussinessException("Paciente não encontrado");
        }

        Optional<Agenda> optHoratio = repository.findByHorario(agenda.getHorario());

        if (optHoratio.isPresent()){
            throw new BussinessException("Existe agendamento para este horário");
        }

        agenda.setPaciente(optPaciente.get());
        agenda.setData_criacao(LocalDateTime.now());

        return repository.save(agenda);
    }
}
