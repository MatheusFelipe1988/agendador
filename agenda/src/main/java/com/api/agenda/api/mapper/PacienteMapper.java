package com.api.agenda.api.mapper;

import com.api.agenda.api.request.PacienteRequest;
import com.api.agenda.api.response.PacienteComplementoResponse;
import com.api.agenda.api.response.PacienteResponse;
import com.api.agenda.domain.entity.Paciente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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

    public PacienteComplementoResponse toPaComplementoRes(Paciente paciente){
        return mapper.map(paciente, PacienteComplementoResponse.class);
    }

    public List<PacienteResponse> toListResponse(List<Paciente> pacientes){
        return pacientes.stream()
                .map(this::toPacienteResponse)
                .collect(Collectors.toList());
    }
}
