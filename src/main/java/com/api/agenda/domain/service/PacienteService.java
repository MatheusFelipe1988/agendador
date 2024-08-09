package com.api.agenda.domain.service;

import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.repository.PacienteRepository;
import com.api.agenda.exception.BussinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PacienteService {

    private final PacienteRepository repository;

    public Paciente saver(Paciente paciente){
        boolean existCpf = false;

        Optional<Paciente> optPaciente = repository.findByCpf(paciente.getCpf());

        if(optPaciente.isEmpty()){
            if (!optPaciente.get().getId().equals(paciente.getId())){
                existCpf = true;
            }
        }
        if (existCpf){
            throw new BussinessException("CPF cadastrado");
        }
        return repository.save(paciente);
    }

    public Paciente alter(Long id, Paciente paciente){
        Optional<Paciente> optional = this.getForId(id);

        if (optional.isEmpty()){
            throw new BussinessException("Paciente não cadastrado");
        }

        paciente.setId(id);

        return saver(paciente);
    }

    public List<Paciente> listALl(){
        return repository.findAll();
    }

    public void deleter(Long id){
        repository.deleteById(id);
    }

    public Optional<Paciente> getForId(Long id) {
        return repository.findById(id);
    }
}
