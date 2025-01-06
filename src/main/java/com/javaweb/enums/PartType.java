package com.javaweb.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PartType {

    PART_1("Part 1"),
    PART_2("Part 2"),
    PART_3("Part 3"),
    PART_4("Part 4"),
    PART_5("Part 5"),
    PART_6("Part 6"),
    PART_7("Part 7");

    private final String partName;

    private PartType(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
    }

    public static Map<String, String> type() {

        Map<String, String> listType = new LinkedHashMap<>();
        for (PartType partType : PartType.values()) {
            listType.put(partType.name(), partType.getPartName());
        }
        return listType;
    }

}
