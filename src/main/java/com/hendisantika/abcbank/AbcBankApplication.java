package com.hendisantika.abcbank;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AbcBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcBankApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

        return modelMapper;
    }
}
