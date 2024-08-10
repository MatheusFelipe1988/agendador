package com.api.agenda.api.mapper;

import com.api.agenda.api.request.AgendaRequest;
import com.api.agenda.api.response.AgendaResponse;
import com.api.agenda.domain.entity.Agenda;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaMapper {

    private final ModelMapper mapper;

    public Agenda toAgenda(AgendaRequest request){
        return mapper.map(request, Agenda.class);
    }

    public AgendaResponse toAgendaResponse(Agenda agenda){
        return mapper.map(agenda, AgendaResponse.class);
    }

    public List<AgendaResponse> toListAgenda(List<Agenda> agendas){
        return agendas.stream().map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
