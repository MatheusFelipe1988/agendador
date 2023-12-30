package com.agenda.agendador.api.controller;


import com.agenda.agendador.api.mapper.AgendaMapper;
import com.agenda.agendador.api.request.AgendaRequest;
import com.agenda.agendador.api.response.AgendaResponse;
import com.agenda.agendador.domain.entity.Agenda;
import com.agenda.agendador.domain.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
    private final AgendaService service;
    private final AgendaMapper mapper;

    @GetMapping
    public ResponseEntity<List<AgendaResponse>> buscarTodos() {
        List<Agenda> agendas = service.listarTodos();
        List<AgendaResponse> agendaResponses = mapper.toAgendaResponseList(agendas);
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> buscarPorid(@PathVariable Long id) {
        Optional<Agenda> optAgenda = service.BuscaPorId(id);
        if (optAgenda.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
    }

    @PostMapping
    public ResponseEntity<AgendaResponse> salvar(@Valid @RequestBody AgendaRequest request) {
        Agenda agenda = mapper.toAgenda(request);
        Agenda agendaSalva = service.salvar(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);

    }

}
