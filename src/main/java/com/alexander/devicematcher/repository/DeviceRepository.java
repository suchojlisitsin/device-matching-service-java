package com.alexander.devicematcher.repository;

import com.alexander.devicematcher.model.Device;

import java.util.Optional;

public interface DeviceRepository {

    Optional<Device> findMatchingDevice(
            String osName,
            String osVersion,
            String browserName,
            String browserVersion
    );

    Device save(Device device);
}