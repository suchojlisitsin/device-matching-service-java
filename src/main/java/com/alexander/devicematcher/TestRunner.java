package com.alexander.devicematcher;

import com.alexander.devicematcher.model.Device;
import com.alexander.devicematcher.repository.DeviceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    private final DeviceRepository repository;

    public TestRunner(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        Device device = new Device();

        device.setId("1");
        device.setHitCount(1);
        device.setOsName("Windows");
        device.setOsVersion("10");
        device.setBrowserName("Chrome");
        device.setBrowserVersion("138");

        repository.save(device);

        System.out.println("test");

        System.out.println(repository.findById("1"));
    }
}
