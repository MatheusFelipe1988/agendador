package com.agenda.agendador.domain.service;
import com.agenda.agendador.domain.entity.Paciente;
import com.agenda.agendador.domain.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {
    @Autowired
    private final PacienteRepository repository;

    public Paciente salvar(Paciente paciente) throws BusinessException {
        boolean existeCpf = false;
        Optional<Paciente> optPaciente = repository.findByCpf(paciente.getCpf());

        if(optPaciente.isPresent()){
            if (!optPaciente.get().getId().equals(paciente.getId())){
                existeCpf = true;
            }
        }
        if(existeCpf){
            throw new BusinessException("cpf ja cadastrado");
        }


        return repository.save(paciente);
    }
    public Paciente alterar (Long id, Paciente paciente){
        Optional<Paciente> optPaciente = this.buscarPorId(id);
        if(optPaciente.isEmpty()){
            throw new BusinessException("Paciente nao cadastrado");
        }
        paciente.setId(id);
        return salvar(paciente);
    }

    public List<Paciente> listarTodos(){
        return repository.findAll();
    }
    public Optional<Paciente> buscarPorId(Long id){
        return repository.findById(id);
    }
    public void deletar(Long id){
        repository.deleteById(id);
    }
}
