package br.com.antunes.gustavo.personapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
