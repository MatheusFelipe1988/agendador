package com.api.agenda.api.controller;

import com.api.agenda.api.mapper.AgendaMapper;
import com.api.agenda.api.request.AgendaRequest;
import com.api.agenda.api.response.AgendaResponse;
import com.api.agenda.domain.entity.Agenda;
import com.api.agenda.domain.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService service;
    private final AgendaMapper mapper;

    @Operation(summary = "Listar todos os agendamentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na listagem")
    })
    @GetMapping
    public ResponseEntity<List<AgendaResponse>> getAll(){
        List<Agenda> agendas = service.listerAll();
        List<AgendaResponse> agendaResponses = mapper.toListAgenda(agendas);

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }

    @Operation(summary = "Bucar por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "busca do agendamento com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na busca")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponse> getForId(@PathVariable Long id){
        Optional<Agenda> optAgenda = service.getById(id);

        if(optAgenda.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);
    }

    @Operation(summary = "Novo agendamento", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "agendado com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro no agendamento")
    })
    @PostMapping("/post")
    public ResponseEntity<AgendaResponse> saver(@Valid @RequestBody AgendaRequest request){
        Agenda agenda = mapper.toAgenda(request);
        Agenda agendaSave = service.saver(agenda);

        AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }
}