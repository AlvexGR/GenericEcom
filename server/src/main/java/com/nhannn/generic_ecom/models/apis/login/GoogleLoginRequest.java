package com.nhannn.generic_ecom.models.apis.login;

import com.nhannn.generic_ecom.models.apis.BaseRequest;

public class GoogleLoginRequest extends BaseRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNubmer;
    private String accessToken;

    public GoogleLoginRequest() {
    }

    public GoogleLoginRequest(String email, String firstName, String lastName, String phoneNubmer, String accessToken) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNubmer = phoneNubmer;
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNubmer() {
        return phoneNubmer;
    }

    public void setPhoneNubmer(String phoneNubmer) {
        this.phoneNubmer = phoneNubmer;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
