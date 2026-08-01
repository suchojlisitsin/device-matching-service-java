package com.alexander.devicematcher.service.parser;

public record ParsedUserAgent (
    String osName,
    String osVersion,
    String browserName,
    String browserVersion
){}
