package com.alexander.devicematcher.dto;

import com.alexander.devicematcher.model.Device;

public record DeviceResponse(
        String id,
        int hitCount,
        String osName,
        String osVersion,
        String browserName,
        String browserVersion
) {

    public static DeviceResponse from(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getHitCount(),
                device.getOsName(),
                device.getOsVersion(),
                device.getBrowserName(),
                device.getBrowserVersion()
        );
    }
}