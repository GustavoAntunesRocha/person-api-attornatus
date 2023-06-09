package br.com.antunes.gustavo.personapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI baseOpenAPI() {
    	return new OpenAPI().info(new Info().title("Gerenciamento de Pessoas").version("1.0.0").description("API para gerenciar pessoas."));
    }
}

