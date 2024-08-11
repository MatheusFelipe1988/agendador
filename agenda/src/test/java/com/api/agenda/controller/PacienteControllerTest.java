package com.api.agenda.controller;

import com.api.agenda.api.request.PacienteRequest;
import com.api.agenda.domain.entity.Paciente;
import com.api.agenda.domain.repository.PacienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PacienteControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PacienteRepository repository;

    @BeforeEach
    void up(){
        Paciente paciente = new Paciente();
        paciente.setNome("Max");
        paciente.setSobreNome("Verstappen");
        paciente.setEmail("max.com");
        paciente.setCpf("123456");
        repository.save(paciente);
    }

    @AfterEach
    void down(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("Listar os pacientes")
    void listPaciente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Listar paciente pelo id")
    void listById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/paciente/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Salvar com sucesso")
    void saverPaciente() throws Exception {
        PacienteRequest request = PacienteRequest.builder()
                .email("maxver@gmail.com")
                .nome("Max")
                .sobreNome("Verstappen")
                .cpf("123456")
                .build();

        String pacienteRequest = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(pacienteRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}
