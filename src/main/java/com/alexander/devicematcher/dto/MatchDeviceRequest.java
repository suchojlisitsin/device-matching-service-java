package com.alexander.devicematcher.dto;

import jakarta.validation.constraints.NotBlank;

public record MatchDeviceRequest(
        @NotBlank(message = "User-Agent must not be blank")
        String userAgent
) {
}