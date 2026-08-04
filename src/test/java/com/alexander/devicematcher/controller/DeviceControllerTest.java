package com.alexander.devicematcher.controller;

import com.alexander.devicematcher.model.Device;
import com.alexander.devicematcher.service.MatcherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatcherService matcherService;

    @Test
    void shouldMatchDevice() throws Exception {

        Device device = new Device();
        device.setId("1");
        device.setHitCount(1);
        device.setOsName("Windows");
        device.setOsVersion("10.0");
        device.setBrowserName("Firefox");
        device.setBrowserVersion("139.0");

        when(matcherService.matchDevice("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) Gecko/20100101 Firefox/139.0"))
                .thenReturn(device);

        mockMvc.perform(post("/api/devices/match")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userAgent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) Gecko/20100101 Firefox/139.0"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.hitCount").value(1))
                .andExpect(jsonPath("$.osName").value("Windows"))
                .andExpect(jsonPath("$.osVersion").value("10.0"))
                .andExpect(jsonPath("$.browserName").value("Firefox"))
                .andExpect(jsonPath("$.browserVersion").value("139.0"));

        verify(matcherService).matchDevice("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) Gecko/20100101 Firefox/139.0");
    }

    @Test
    void shouldGetDeviceById() throws Exception {

        Device device = new Device();
        device.setId("1");
        device.setHitCount(5);
        device.setOsName("Windows");
        device.setOsVersion("10.0");
        device.setBrowserName("Firefox");
        device.setBrowserVersion("139.0");

        when(matcherService.getDeviceById("1"))
                .thenReturn(device);

        mockMvc.perform(get("/api/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.hitCount").value(5));

        verify(matcherService).getDeviceById("1");
    }

    @Test
    void shouldGetDevicesByOsName() throws Exception {

        Device device = new Device();
        device.setId("1");
        device.setHitCount(3);
        device.setOsName("Windows");
        device.setOsVersion("10.0");
        device.setBrowserName("Firefox");
        device.setBrowserVersion("139.0");

        when(matcherService.getDevicesByOsName("Windows"))
                .thenReturn(List.of(device));

        mockMvc.perform(get("/api/devices")
                        .param("osName", "Windows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].osName").value("Windows"));

        verify(matcherService).getDevicesByOsName("Windows");
    }

    @Test
    void shouldDeleteDevices() throws Exception {

        doNothing().when(matcherService)
                .deleteDevices(anyList());

        mockMvc.perform(delete("/api/devices")
                        .param("ids", "1", "2"))
                .andExpect(status().isNoContent());

        verify(matcherService)
                .deleteDevices(List.of("1", "2"));
    }

    @Test
    void shouldReturnBadRequestWhenDeletingWithoutIds() throws Exception {

        mockMvc.perform(delete("/api/devices"))
                .andExpect(status().isBadRequest());
    }
}