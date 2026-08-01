package com.alexander.devicematcher.service;

import com.alexander.devicematcher.service.parser.ParsedUserAgent;
import com.alexander.devicematcher.service.parser.UserAgentParser;

public class MatcherService {
    private final UserAgentParser userAgentParser;

    public MatcherService(UserAgentParser userAgentParser){
        this.userAgentParser = userAgentParser;
    }

    //only used for testing
    public ParsedUserAgent parse(String userAgent){
        return userAgentParser.getParsedUserAgent(userAgent);
    }
}
