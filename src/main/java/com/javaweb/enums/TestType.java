package com.javaweb.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TestType {

    Full_Test("Full Test"),
    Parts_Test("Parts Test");

    private final String testName;

    private TestType(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public static Map<String, String> type() {

        Map<String, String> listType = new LinkedHashMap<>();
        for (TestType testType : TestType.values()) {
            listType.put(testType.name(), testType.getTestName());
        }
        return listType;
    }

}
