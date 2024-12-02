package com.example.pcbdispatcher.enums;

import lombok.Getter;

@Getter
public enum BoardStatus {
    REGISTRATION("Registration"),
    COMPONENT_INSTALLATION("Component installation"),
    QUALITY_CONTROL("Quality control"),
    REPAIR("Repair"),
    PACKAGING("Packaging");

    private final String description;
    BoardStatus(String description) {
        this.description = description;
    }
}
