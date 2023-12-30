package com.agenda.agendador.domain.service;

import com.agenda.agendador.domain.entity.Agenda;
import com.agenda.agendador.domain.entity.Paciente;
import com.agenda.agendador.domain.repository.AgendaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {
    @InjectMocks
    AgendaService service;

    @Mock
    PacienteService pacienteService;

    @Mock
    AgendaRepository repository;

    @Captor
    ArgumentCaptor<Agenda> agendaCaptor;

    @Test
    @DisplayName("SalvoComSucesso")
    void salvarComSucesso() {

        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(LocalDateTime.now());

        Paciente paciente = new Paciente();
        paciente.setId(4L);
        paciente.setNome("Pierre");

        Mockito.when(pacienteService.buscarPorId(agenda.getPaciente().getId())).thenReturn(Optional.of(paciente));

        agenda.setPaciente(paciente);

        //asserctions
        Mockito.verify(pacienteService).buscarPorId(ArgumentMatchers.any());
        Mockito.verify(pacienteService).buscarPorId(agenda.getPaciente().getId());
        Mockito.verify(repository).findByHorario(LocalDateTime.now());

        Mockito.verify(repository).save(agendaCaptor.capture());
        Mockito.when(repository.findByHorario(LocalDateTime.now())).thenReturn(Optional.empty());
        Agenda agendaSalva = agendaCaptor.getValue();

        Assertions.assertThat(agendaSalva.getPaciente()).isNotNull();
        Assertions.assertThat(agendaSalva.getDataCriacao()).isNotNull();
    }
    @Test
    @DisplayName("Nao pode ser salvo sem o paciente")
    void salvarErroPacienteNaoEncontrado(){
        LocalDateTime now = LocalDateTime.now();

        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(ArgumentMatchers.any())).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            service.salvar(agenda);
        });
        Assertions.assertThat(businessException.getMessage()).isEqualTo("Paciente nao encontrado");
    }
}