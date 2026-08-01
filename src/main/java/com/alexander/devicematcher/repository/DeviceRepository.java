package com.alexander.devicematcher.repository;

import com.alexander.devicematcher.model.Device;
import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository
        extends AerospikeRepository<Device, String> {

    Optional<Device>
    findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
            String osName,
            String osVersion,
            String browserName,
            String browserVersion
    );

    List<Device> findAllByOsName(String osName);
}