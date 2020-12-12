package com.bruno.testwork.framework;

import java.util.Arrays;
import java.util.List;

public enum TestType {

    WEB_CHROME("webdriver.chrome.driver"),
    WEB_FIREFOX("webdriver.gecko.driver"),
    WEB_OPERA("webdriver.opera.driver"),
    WEB_EDGE(null),
    DESKTOP_AUTOIT(null),
    DESKTOP_SIKULI(null),
    DEKSTOP_APPIUM(null),
    DEKSTOP_WINIUM(null),
    ANDROID(null),
    IOS(null);

    private final String driverName;

    TestType(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public static boolean isWeb(TestType type) {
        List<TestType> list = Arrays.asList(WEB_CHROME, WEB_FIREFOX, WEB_OPERA, WEB_EDGE);
        return list.contains(type);
    }

    public static boolean isDesktop(TestType type) {
        List<TestType> list = Arrays.asList(DESKTOP_AUTOIT, DESKTOP_SIKULI, DEKSTOP_APPIUM, DEKSTOP_WINIUM);
        return list.contains(type);
    }

    public static boolean isMobile(TestType type) {
        List<TestType> list = Arrays.asList(ANDROID, IOS);
        return list.contains(type);
    }

    public static TestType[] getTypes() {
        return new TestType[]{
                WEB_CHROME, WEB_FIREFOX, WEB_OPERA, WEB_EDGE,
                DESKTOP_AUTOIT, DESKTOP_SIKULI, DEKSTOP_APPIUM, DEKSTOP_WINIUM,
                ANDROID, IOS
        };
    }

}
