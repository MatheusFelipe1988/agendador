package com.api.agenda.api.controller;

import com.api.agenda.api.mapper.PacienteMapper;
import com.api.agenda.api.request.PacienteRequest;
import com.api.agenda.api.response.PacienteComplementoResponse;
import com.api.agenda.api.response.PacienteResponse;
import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;
    private final Logger log = LoggerFactory.getLogger(PacienteController.class);

    @Operation(summary = "Adicionar novos pacientes", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente novo com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro no cadastro")
    })
    @PostMapping
    public ResponseEntity<PacienteResponse> saver(@Valid @RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.saver(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse);
    }

    @Operation(summary = "Listar todos os dados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listados com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na listagem")
    })
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listAll(){
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

        List<PacienteResponse> pacienteResponses = service.listALl()
                .stream().map(mapper::toPacienteResponse)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponses);
    }

    @Operation(summary = "buscar por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dado buscado com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na busca")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteComplementoResponse> getById(@PathVariable Long id){
        log.info("realizando busca do paciente por id: {}", id);

        return service.getForId(id)
                .map(mapper::toPaComplementoRes)
                .map(pacienteComplementoResponse -> ResponseEntity.status(HttpStatus.OK)
                        .body(pacienteComplementoResponse)).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar Paciente", method = "PuT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dado alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na alteração")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> alter(@PathVariable Long id, @RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.alter(id, paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @Operation(summary = "Remover Paciente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "erro na remoção")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleter(@PathVariable Long id){
        service.deleter(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
