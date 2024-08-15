package com.api.agenda.service;

import com.api.agenda.configuration.exception.BussinessException;
import com.api.agenda.domain.entity.Agenda;
import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.repository.AgendaRepository;
import com.api.agenda.domain.service.AgendaService;
import com.api.agenda.domain.service.PacienteService;
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
    AgendaService agendaService;

    @Mock
    PacienteService service;

    @Mock
    AgendaRepository repository;

    @Captor
    ArgumentCaptor<Agenda> captor;

    @Test
    @DisplayName("Deve salvar com sucesso")
    void saveWithSuccess(){

        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(service.getForId(agenda.getPaciente().getId())).thenReturn(Optional.of(paciente));
        Mockito.when(repository.findByHorario(now)).thenReturn(Optional.empty());

        agendaService.saver(agenda);

        Mockito.verify(service).getForId(ArgumentMatchers.any());
        Mockito.verify(service).getForId(agenda.getPaciente().getId());
        Mockito.verify(repository).findByHorario(now);

        Mockito.verify(repository).save(captor.capture());
        Agenda agendaSave = captor.getValue();

        Assertions.assertThat(agendaSave.getPaciente()).isNotNull();
        Assertions.assertThat(agendaSave.getData_criacao()).isNotNull();
    }

    @Test
    @DisplayName("Não deve salvar agendamento sem paciente")
    void salvarErroPacienteNaoEncontrado(){

        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descricao do agendamento");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Daniel");

        agenda.setPaciente(paciente);

        Mockito.when(service.getForId(ArgumentMatchers.any())).thenReturn(Optional.empty());

        BussinessException exception = assertThrows(BussinessException.class, () -> agendaService.saver(agenda));

        Assertions.assertThat(exception.getMessage()).isEqualTo("Paciente não encontrado");
    }
}
