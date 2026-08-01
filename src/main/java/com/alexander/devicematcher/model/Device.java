package com.alexander.devicematcher.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.aerospike.mapping.Document;

@Document(collection = "devices")
public class Device {

    @Id
    private String id;

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", hitCount=" + hitCount +
                ", osName='" + osName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                '}';
    }

    private int hitCount;
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }
}