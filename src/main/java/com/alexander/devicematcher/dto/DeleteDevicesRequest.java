package com.alexander.devicematcher.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record DeleteDevicesRequest(
        @NotEmpty(message = "At least one device ID is required")
        List<String> ids
) {
}