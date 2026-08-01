package com.alexander.devicematcher.service.parser;

import ua_parser.Client;
import ua_parser.Parser;


public class UserAgentParser {

    private final Parser parser;

    public UserAgentParser() {

        parser = new Parser();
    }

    public ParsedUserAgent getParsedUserAgent(String userAgent) {
        Client client = parser.parse(userAgent);

        String osName = client.os.family;

        String osVersion = "";
        if (client.os.major != null) {
            osVersion += client.os.major;
        }
        if (client.os.minor != null) {
            osVersion += "." + client.os.minor;
        }

        String browserName = client.userAgent.family;

        String browserVersion = "";
        if (client.userAgent.major != null) {
            browserVersion += client.userAgent.major;
        }
        if (client.userAgent.minor != null) {
            browserVersion += "." + client.userAgent.minor;
        }
        if (client.userAgent.patch != null) {
            browserVersion += "." + client.userAgent.patch;
        }

        System.out.println(client);

        return new ParsedUserAgent(
                osName,
                osVersion,
                browserName,
                browserVersion
        );
    }
}