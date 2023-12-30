package com.agenda.agendador.api.mapper;


import com.agenda.agendador.api.request.AgendaRequest;
import com.agenda.agendador.api.response.AgendaResponse;
import com.agenda.agendador.domain.entity.Agenda;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaMapper {
    public final ModelMapper mapper;

    public Agenda toAgenda(AgendaRequest request){
        return mapper.map(request, Agenda.class);

    }
    public AgendaResponse toAgendaResponse(Agenda agenda){
        return mapper.map(agenda, AgendaResponse.class);
    }
    public List<AgendaResponse> toAgendaResponseList(List<Agenda> agendas){
        return agendas.stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
