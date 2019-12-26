package com.nhannn.generic_ecom.models.apis.sign_up;

import com.nhannn.generic_ecom.models.apis.BaseResponse;

/**
 * Author: nhannn
 */
public class SignUpResponse extends BaseResponse {
    boolean success;

    public SignUpResponse() {
    }

    public SignUpResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
