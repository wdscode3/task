package org.wds.taskmanager.domain.enums;

public enum RefreshEnum {
    S5(5000),
    S10(10000),
    S15(15000),
    S30(30000),
    M1(60000),
    M5(300000),
    M10(600000),
    M15(900000),
    M30(1800000),
    H1(3600000),
    H2(7200000),
    H4(14400000),
    H8(28800000),
    D1(86400000);

    private final int value;

    RefreshEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
