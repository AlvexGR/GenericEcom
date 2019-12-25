package com.nhannn.generic_ecom.helpers.enums;

public enum UserStatus {
    ACTIVE(1),
    DEACTIVATED(0),
    DELETED(2);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
