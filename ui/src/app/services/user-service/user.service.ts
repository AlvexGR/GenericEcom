import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "src/app/models/user.model";
import { SignUpResponse } from "src/app/models/api/sign-up/sign-up.response";
import { HttpHelper } from "src/app/helpers/http.helper";
import { SignUpRequest } from "src/app/models/api/sign-up/sign-up.request";
import { LoginResponse } from "src/app/models/api/login/login.response";
import { LoginRequest } from "src/app/models/api/login/login.request";

/**
 * Author: nhannn
 */
@Injectable({
  providedIn: "root"
})
export class UserService {
  constructor(private http: HttpClient) {}

  /**
   * Sign up new account
   * @param user new account
   */
  public async signUp(user: User): Promise<SignUpResponse> {
    const request = new SignUpRequest(user);
    const headers = HttpHelper.createHeader();
    const url = HttpHelper.baseUrl + HttpHelper.usersUrl + "sign-up";
    try {
      const response = await this.http
        .post<SignUpResponse>(url, request, { headers, observe: "response" })
        .toPromise();
      return response.body;
    } catch (err) {
      console.log(err);
      return null;
    }
  }

  /**
   * Basic login with email and password
   * @param email credentials
   * @param password credentials
   */
  public async login(email: string, password: string): Promise<LoginResponse> {
    const request = new LoginRequest(email, password);
    const headers = HttpHelper.createHeader();
    const url = HttpHelper.baseUrl + HttpHelper.usersUrl + "login";
    try {
      const response = await this.http
        .post<LoginResponse>(url, request, { headers, observe: "response" })
        .toPromise();
      return response.body;
    } catch (err) {
      console.log(err);
      return null;
    }
  }
}
