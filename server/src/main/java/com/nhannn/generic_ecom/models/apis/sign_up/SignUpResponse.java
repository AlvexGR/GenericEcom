package com.nhannn.generic_ecom.models.apis.sign_up;

import com.nhannn.generic_ecom.helpers.enums.ErrorCode;
import com.nhannn.generic_ecom.models.apis.BaseResponse;

/**
 * Author: nhannn
 */
public class SignUpResponse extends BaseResponse {
    public SignUpResponse() {
    }

    public SignUpResponse(ErrorCode success) {
        super(success);
    }
}
