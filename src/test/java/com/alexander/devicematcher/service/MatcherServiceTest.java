package com.alexander.devicematcher.service;

import com.alexander.devicematcher.model.Device;
import com.alexander.devicematcher.repository.DeviceRepository;
import com.alexander.devicematcher.service.parser.ParsedUserAgent;
import com.alexander.devicematcher.service.parser.UserAgentParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.aerospike.query.QueryParam;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatcherServiceTest {

    @Mock
    private UserAgentParser userAgentParser;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private MatcherService deviceMatchingService;

    @Test
    void shouldCreateNewDeviceWhenNoMatchingDeviceExists() {
        String userAgent = "test-user-agent";

        ParsedUserAgent parsedUserAgent = new ParsedUserAgent(
                "Windows",
                "10.0",
                "Chrome",
                "138.0.0"
        );

        when(userAgentParser.getParsedUserAgent(userAgent))
                .thenReturn(parsedUserAgent);

        when(deviceRepository
                .findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class)
                ))
                .thenReturn(Optional.empty());

        when(deviceRepository.save(any(Device.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Device result = deviceMatchingService.matchDevice(userAgent);

        assertNotNull(result.getId());
        assertEquals(1, result.getHitCount());
        assertEquals("Windows", result.getOsName());
        assertEquals("10.0", result.getOsVersion());
        assertEquals("Chrome", result.getBrowserName());
        assertEquals("138.0.0", result.getBrowserVersion());

        verify(userAgentParser)
                .getParsedUserAgent(userAgent);

        verify(deviceRepository)
                .findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class)
                );

        ArgumentCaptor<Device> deviceCaptor =
                ArgumentCaptor.forClass(Device.class);

        verify(deviceRepository).save(deviceCaptor.capture());

        Device savedDevice = deviceCaptor.getValue();

        assertNotNull(savedDevice.getId());
        assertEquals(1, savedDevice.getHitCount());
        assertEquals("Windows", savedDevice.getOsName());
        assertEquals("10.0", savedDevice.getOsVersion());
        assertEquals("Chrome", savedDevice.getBrowserName());
        assertEquals("138.0.0", savedDevice.getBrowserVersion());
    }

    @Test
    void shouldIncrementHitCountWhenMatchingDeviceExists() {
        String userAgent = "test-user-agent";

        ParsedUserAgent parsedUserAgent = new ParsedUserAgent(
                "Windows",
                "10.0",
                "Chrome",
                "138.0.0"
        );

        Device existingDevice = new Device();
        existingDevice.setId("device-123");
        existingDevice.setHitCount(4);
        existingDevice.setOsName("Windows");
        existingDevice.setOsVersion("10.0");
        existingDevice.setBrowserName("Chrome");
        existingDevice.setBrowserVersion("138.0.0");

        when(userAgentParser.getParsedUserAgent(userAgent))
                .thenReturn(parsedUserAgent);

        when(deviceRepository
                .findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class)
                ))
                .thenReturn(Optional.of(existingDevice));

        when(deviceRepository.save(existingDevice))
                .thenReturn(existingDevice);

        Device result = deviceMatchingService.matchDevice(userAgent);

        assertEquals("device-123", result.getId());
        assertEquals(5, result.getHitCount());
        assertEquals("Windows", result.getOsName());
        assertEquals("10.0", result.getOsVersion());
        assertEquals("Chrome", result.getBrowserName());
        assertEquals("138.0.0", result.getBrowserVersion());

        verify(userAgentParser)
                .getParsedUserAgent(userAgent);

        verify(deviceRepository)
                .findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class),
                        any(QueryParam.class)
                );

        verify(deviceRepository).save(existingDevice);
    }
}