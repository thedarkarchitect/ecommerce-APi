package com.example.ecommerce.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Spring annotation to specify that this class contains configuration methods
public class ModelMapperConfig {

    @Bean // Spring annotation to register a new bean in the application context
    public ModelMapper modelMapper() {
        return new ModelMapper(); // Return a new instance of ModelMapper
    }
}
