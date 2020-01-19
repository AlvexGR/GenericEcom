package com.nhannn.generic_ecom.models.apis;

import com.nhannn.generic_ecom.helpers.enums.ErrorCode;

/**
 * Author: nhannn
 */
public class BaseResponse {
    protected ErrorCode errorCode;

    public BaseResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseResponse() {
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
