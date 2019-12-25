package com.nhannn.generic_ecom.helpers.enums;

public enum Role {
    ADMIN(1),
    CUSTOMER(2),
    OWNER(3),
    STAFF(4);

    private final int code;
    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
