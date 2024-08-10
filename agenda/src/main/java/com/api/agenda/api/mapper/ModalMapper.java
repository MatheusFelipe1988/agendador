package com.api.agenda.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModalMapper {

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
