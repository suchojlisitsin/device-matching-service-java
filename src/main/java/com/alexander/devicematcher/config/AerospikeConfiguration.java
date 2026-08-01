package com.alexander.devicematcher.config;

import com.alexander.devicematcher.repository.DeviceRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.aerospike.config.AbstractAerospikeDataConfiguration;
import org.springframework.data.aerospike.repository.config.EnableAerospikeRepositories;

@Configuration
@EnableAerospikeRepositories(basePackageClasses = DeviceRepository.class)
public class AerospikeConfiguration
        extends AbstractAerospikeDataConfiguration {
}