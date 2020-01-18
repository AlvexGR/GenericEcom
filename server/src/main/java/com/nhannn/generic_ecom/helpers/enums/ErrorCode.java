package com.nhannn.generic_ecom.helpers.enums;

/**
 * Author: nhannn
 */
public enum ErrorCode {
    SUCCESS,
    FIRST_NAME_EMPTY,
    LAST_NAME_EMPTY,
    EMAIL_EMPTY,
    EMAIL_NOT_VALID,
    PHONE_NUMBER_EMPTY,
    PHONE_NUMBER_NOT_VALID,
    PASSWORD_EMPTY,
    PASSWORD_CONFIRM_EMPTY,
    ACCOUNT_NOT_EXISTED,
    EMAIL_EXISTED,
    PHONE_NUMBER_EXISTED,
    PASSWORD_CONVENTION_FAILED,
    PASSWORD_NOT_MATCHED,
    LOGIN_FAILED,

    /* General errors */
    INTERNAL_ERROR;
}
