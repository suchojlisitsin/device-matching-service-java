package com.alexander.devicematcher.controller;

import com.alexander.devicematcher.dto.DeviceResponse;
import com.alexander.devicematcher.dto.MatchDeviceRequest;
import com.alexander.devicematcher.model.Device;
import com.alexander.devicematcher.service.MatcherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final MatcherService matcherService;

    public DeviceController(MatcherService matcherService) {
        this.matcherService = matcherService;
    }

    @PostMapping("/match")
    public ResponseEntity<DeviceResponse> matchDevice(
            @Valid @RequestBody MatchDeviceRequest request
    ) {
        Device device =
                matcherService.matchDevice(request.userAgent());

        return ResponseEntity.ok(DeviceResponse.from(device));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(
            @PathVariable String id
    ) {
        Device device = matcherService.getDeviceById(id);

        return ResponseEntity.ok(DeviceResponse.from(device));
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getDevicesByOsName(
            @RequestParam String osName
    ) {
        List<DeviceResponse> devices =
                matcherService.getDevicesByOsName(osName)
                        .stream()
                        .map(DeviceResponse::from)
                        .toList();

        return ResponseEntity.ok(devices);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteDevices(
            //RequestParam instead of RequestBody as the docs discourage bodies in DELETE
            @RequestParam List<String> ids
    ) {
        if (ids.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one device ID is required"
            );
        }

        matcherService.deleteDevices(ids);

        return ResponseEntity.noContent().build();
    }
}