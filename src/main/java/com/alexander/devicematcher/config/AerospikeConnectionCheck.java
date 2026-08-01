package com.alexander.devicematcher.config;

import com.alexander.devicematcher.repository.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConnectionCheck {

    @Bean
    CommandLineRunner checkAerospike(DeviceRepository deviceRepository) {
        return args -> {
            long count = deviceRepository.count();

            System.out.println(
                    "Connected to Aerospike. Device count: " + count
            );
        };
    }
}