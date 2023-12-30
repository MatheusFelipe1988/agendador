package com.agenda.agendador.api.mapper;

import com.agenda.agendador.api.request.PacienteRequest;
import com.agenda.agendador.api.response.PacienteResponse;
import com.agenda.agendador.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final ModelMapper mapper;

    public Paciente toPaciente(PacienteRequest request){
        return mapper.map(request, Paciente.class);

    }
    public PacienteResponse toPacienteResponse(Paciente paciente){
        return mapper.map(paciente, PacienteResponse.class);
    }
    public List<PacienteResponse> toPacienteResponseList(List<Paciente> pacientes){
        return pacientes.stream()
                .map(this::toPacienteResponse)
                .collect(Collectors.toList());
    }
}
