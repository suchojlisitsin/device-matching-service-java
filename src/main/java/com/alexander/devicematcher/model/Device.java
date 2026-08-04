package com.alexander.devicematcher.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.aerospike.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "devices")
public class Device {

    @Id
    private String id;

    private int hitCount;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}