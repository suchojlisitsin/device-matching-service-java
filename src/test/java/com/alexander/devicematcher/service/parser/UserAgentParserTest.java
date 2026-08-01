package com.alexander.devicematcher.service.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAgentParserTest {

    private final UserAgentParser userAgentParser = new UserAgentParser();

    @Test
    void shouldParseWindowsChrome() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/138.0.0.0 Safari/537.36"
        );

        assertEquals("Windows", parsed.osName());
        assertEquals("10", parsed.osVersion());
        assertEquals("Chrome", parsed.browserName());
        assertEquals("138.0.0", parsed.browserVersion());
    }

    @Test
    void shouldParseWindowsFirefox() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        assertEquals("Windows", parsed.osName());
        assertEquals("10", parsed.osVersion());
        assertEquals("Firefox", parsed.browserName());
        assertEquals("139.0", parsed.browserVersion());
    }

    @Test
    void shouldParseMacChrome() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 15_5) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/138.0.0.0 Safari/537.36"
        );

        assertEquals("Mac OS X", parsed.osName());
        assertEquals("15.5", parsed.osVersion());
        assertEquals("Chrome", parsed.browserName());
        assertEquals("138.0.0", parsed.browserVersion());
    }

    @Test
    void shouldParseMacFirefox() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 15.5; rv:139.0) " +
                        "Gecko/20100101 Firefox/139.0"
        );

        assertEquals("Mac OS X", parsed.osName());
        assertEquals("15.5", parsed.osVersion());
        assertEquals("Firefox", parsed.browserName());
        assertEquals("139.0", parsed.browserVersion());
    }

    @Test
    void shouldParseMacSafari() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 15_5) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/18.5 Safari/605.1.15"
        );

        assertEquals("Mac OS X", parsed.osName());
        assertEquals("15.5", parsed.osVersion());
        assertEquals("Safari", parsed.browserName());
        assertEquals("18.5", parsed.browserVersion());
    }

    @Test
    void shouldParseAndroidChrome() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Linux; Android 15; Pixel 9) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/138.0.0.0 Mobile Safari/537.36"
        );

        assertEquals("Android", parsed.osName());
        assertEquals("15", parsed.osVersion());
        assertEquals("Chrome Mobile", parsed.browserName());
        assertEquals("138.0.0", parsed.browserVersion());
    }

    @Test
    void shouldParseAndroidFirefox() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Android 15; Mobile; rv:139.0) " +
                        "Gecko/139.0 Firefox/139.0"
        );

        assertEquals("Android", parsed.osName());
        assertEquals("15", parsed.osVersion());
        assertEquals("Firefox Mobile", parsed.browserName());
        assertEquals("139.0", parsed.browserVersion());
    }

    @Test
    void shouldParseIOSChrome() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "CriOS/138.0.0.0 Mobile/15E148 Safari/604.1"
        );

        assertEquals("iOS", parsed.osName());
        assertEquals("18.5", parsed.osVersion());
        assertEquals("Chrome Mobile iOS", parsed.browserName());
        assertEquals("138.0.0", parsed.browserVersion());
    }

    @Test
    void shouldParseIOSFirefox() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "FxiOS/139.0 Mobile/15E148 Safari/605.1.15"
        );

        assertEquals("iOS", parsed.osName());
        assertEquals("18.5", parsed.osVersion());
        assertEquals("Firefox iOS", parsed.browserName());
        assertEquals("139.0", parsed.browserVersion());
    }

    @Test
    void shouldParseIOSSafari() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/18.5 Mobile/15E148 Safari/604.1"
        );

        assertEquals("iOS", parsed.osName());
        assertEquals("18.5", parsed.osVersion());
        assertEquals("Mobile Safari", parsed.browserName());
        assertEquals("18.5", parsed.browserVersion());
    }

    @Test
    void shouldParseMajorOnlyOsVersion() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Linux; Android 15; Pixel 9) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/138.0.0.0 Mobile Safari/537.36"
        );

        assertEquals("15", parsed.osVersion());
    }

    @Test
    void shouldParseMajorAndMinorOsVersion() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 15_5) " +
                        "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                        "Version/18.5 Safari/605.1.15"
        );

        assertEquals("15.5", parsed.osVersion());
    }


    @Test
    void shouldParseMajorAndMinorBrowserVersion() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:139.5) " +
                        "Gecko/20100101 Firefox/139.5"
        );

        assertEquals("139.5", parsed.browserVersion());
    }

    @Test
    void shouldParseMajorMinorAndPatchBrowserVersion() {
        ParsedUserAgent parsed = userAgentParser.getParsedUserAgent(
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/138.2.7.0 Safari/537.36"
        );

        assertEquals("138.2.7", parsed.browserVersion());
        assertEquals("10", parsed.osVersion());
    }
}