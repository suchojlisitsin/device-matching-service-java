package com.alexander.devicematcher.service.parser;

import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;


@Component
public class UserAgentParser {

    private final Parser parser;

    public UserAgentParser() {
        parser = new Parser();
    }

    public ParsedUserAgent getParsedUserAgent(String userAgent) {
        Client client = parser.parse(userAgent);

        String osName = client.os.family;
        String osVersion = buildVersion(
                client.os.major,
                client.os.minor,
                client.os.patch
        );

        String browserName = client.userAgent.family;
        String browserVersion = buildVersion(
                client.userAgent.major,
                client.userAgent.minor,
                client.userAgent.patch
        );

        return new ParsedUserAgent(
                osName,
                osVersion,
                browserName,
                browserVersion
        );
    }

    private String buildVersion(
            String major,
            String minor,
            String patch
    ) {
        StringBuilder version = new StringBuilder();

        if (major != null && !major.isBlank()) {
            version.append(major);
        }

        if (minor != null && !minor.isBlank()) {
            version.append(".").append(minor);
        }

        if (patch != null && !patch.isBlank()) {
            version.append(".").append(patch);
        }

        return version.toString();
    }
}