package com.alexander.devicematcher.service;

import com.alexander.devicematcher.model.Device;
import com.alexander.devicematcher.repository.DeviceRepository;
import com.alexander.devicematcher.service.parser.ParsedUserAgent;
import com.alexander.devicematcher.service.parser.UserAgentParser;
import org.springframework.data.aerospike.query.QueryParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MatcherService {

    private final UserAgentParser userAgentParser;
    private final DeviceRepository deviceRepository;

    public MatcherService(
            UserAgentParser userAgentParser,
            DeviceRepository deviceRepository
    ) {
        this.userAgentParser = userAgentParser;
        this.deviceRepository = deviceRepository;
    }

    public Device matchDevice(String userAgent) {
        ParsedUserAgent parsed =
                userAgentParser.getParsedUserAgent(userAgent);

        return deviceRepository.findByOsNameAndOsVersionAndBrowserNameAndBrowserVersion(
                        QueryParam.of(parsed.osName()),
                QueryParam.of(parsed.osVersion()),
                QueryParam.of(parsed.browserName()),
                QueryParam.of(parsed.browserVersion())
                )
                .map(existing -> {
                    existing.setHitCount(existing.getHitCount() + 1);

                    return deviceRepository.save(existing);
                })
                .orElseGet(() -> {
                    Device device = new Device();

                    device.setId(UUID.randomUUID().toString());
                    device.setHitCount(1);
                    device.setOsName(parsed.osName());
                    device.setOsVersion(parsed.osVersion());
                    device.setBrowserName(parsed.browserName());
                    device.setBrowserVersion(parsed.browserVersion());

                    return deviceRepository.save(device);
                });
    }
    public Device getDeviceById(String id) {
        return deviceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Device not found with ID: " + id
                        )
                );
    }

    public List<Device> getDevicesByOsName(String osName) {
        return deviceRepository.findAllByOsName(osName);
    }

    public void deleteDevices(List<String> ids) {
        deviceRepository.deleteAllById(ids);
    }
}