package com.nhannn.generic_ecom.models.apis;

/**
 * Author: nhannn
 */
public class BaseResponse {
    protected boolean success;

    public BaseResponse(boolean success) {
        this.success = success;
    }

    public BaseResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
