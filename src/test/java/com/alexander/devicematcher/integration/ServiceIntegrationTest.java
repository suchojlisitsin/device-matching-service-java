package com.alexander.devicematcher.integration;

import com.alexander.devicematcher.dto.DeviceResponse;
import com.alexander.devicematcher.dto.MatchDeviceRequest;
import com.alexander.devicematcher.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureRestTestClient
class ServiceIntegrationTest {

    @Autowired
    private RestTestClient restClient;

    @Autowired
    private DeviceRepository deviceRepository;

    @BeforeEach
    void cleanDatabase() {
        deviceRepository.deleteAll();
    }

    @Test
    void shouldCreateAndThenMatchExistingDevice() {
        MatchDeviceRequest request = new MatchDeviceRequest(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        DeviceResponse firstResponse = restClient
                .post()
                .uri("/api/devices/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeviceResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(firstResponse);
        assertNotNull(firstResponse.id());
        assertEquals(1, firstResponse.hitCount());
        assertEquals("Windows", firstResponse.osName());
        assertEquals("10", firstResponse.osVersion());
        assertEquals("Firefox", firstResponse.browserName());
        assertEquals("139.0", firstResponse.browserVersion());

        DeviceResponse secondResponse = restClient
                .post()
                .uri("/api/devices/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeviceResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(secondResponse);
        assertEquals(firstResponse.id(), secondResponse.id());
        assertEquals(2, secondResponse.hitCount());
    }

    @Test
    void shouldGetDeviceById() {
        MatchDeviceRequest request = new MatchDeviceRequest(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        DeviceResponse createdDevice = restClient
                .post()
                .uri("/api/devices/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeviceResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdDevice);

        restClient
                .get()
                .uri("/api/devices/{id}", createdDevice.id())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeviceResponse.class)
                .value(response -> {
                    assertEquals(createdDevice.id(), response.id());
                    assertEquals("Firefox", response.browserName());
                    assertEquals(1, response.hitCount());
                });
    }

    @Test
    void shouldGetDevicesByOsName() {
        MatchDeviceRequest request = new MatchDeviceRequest(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        restClient
                .post()
                .uri("/api/devices/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk();

        restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/devices")
                        .queryParam("osName", "Windows")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(
                        new ParameterizedTypeReference<List<DeviceResponse>>() {}
                )
                .value(devices -> {
                    assertNotNull(devices);
                    assertEquals(1, devices.size());
                    assertEquals("Windows", devices.getFirst().osName());
                    assertEquals("Firefox", devices.getFirst().browserName());
                });
    }
    @Test
    void shouldDeleteDevices() {
        MatchDeviceRequest request = new MatchDeviceRequest(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        DeviceResponse createdDevice = restClient
                .post()
                .uri("/api/devices/match")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(DeviceResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdDevice);

        restClient
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/devices")
                        .queryParam("ids", createdDevice.id())
                        .build())
                .exchange()
                .expectStatus()
                .isNoContent();

        assertEquals(false, deviceRepository.existsById(createdDevice.id()));
    }
}