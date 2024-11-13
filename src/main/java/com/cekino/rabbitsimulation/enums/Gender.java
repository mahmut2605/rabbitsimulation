package com.cekino.rabbitsimulation.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");
    private final String value;

    Gender(String v) {
        value = v;
    }
}