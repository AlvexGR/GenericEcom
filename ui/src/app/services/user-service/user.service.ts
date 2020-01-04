import { GoogleLoginRequest } from "./../../models/api/login/google-login.request";
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { User } from "src/app/models/user.model";
import { SignUpResponse } from "src/app/models/api/sign-up/sign-up.response";
import { SignUpRequest } from "src/app/models/api/sign-up/sign-up.request";
import { HttpHelper } from "src/app/helpers/http.helper";
import { LoginResponse } from "src/app/models/api/login/login.response";
import { LoginRequest } from "src/app/models/api/login/login.request";
import { auth } from "firebase/app";
import { AngularFireAuth } from "@angular/fire/auth";

/**
 * Author: nhannn
 */
@Injectable({
  providedIn: "root"
})
export class UserService {
  constructor(private http: HttpClient, private fireAuth: AngularFireAuth) {}

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
   * Direct login with email and password
   * @param email credentials
   * @param password credentials
   */
  public async login(
    email: string,
    password: string,
    rememberMe: boolean = false
  ): Promise<LoginResponse> {
    const request = new LoginRequest(email, password, rememberMe);
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

  /**
   * Google login
   */
  public async loginAsGoogle(): Promise<LoginResponse> {
    try {
      const authUser = await this.fireAuth.auth.signInWithPopup(
        new auth.GoogleAuthProvider()
      );
      const googleLoginRequest = new GoogleLoginRequest(
        authUser.additionalUserInfo.profile["email"],
        authUser.additionalUserInfo.profile["given_name"],
        authUser.additionalUserInfo.profile["family_name"],
        authUser.user.phoneNumber,
        authUser.credential["accessToken"]
      );
      const headers = HttpHelper.createHeader();
      const url = HttpHelper.baseUrl + HttpHelper.usersUrl + "login/google";
      const response = await this.http
        .post<LoginResponse>(url, googleLoginRequest, { headers, observe: "response" })
        .toPromise();
      return response.body;
    } catch (err) {
      console.log(err);
      return null;
    }
  }

  /**
   * TODO: Facebook login
   */
  // public async loginAsFacebook(): Promise<void> {
  //   const credential = await this.fireAuth.auth.signInWithPopup(
  //     new auth.FacebookAuthProvider()
  //   );
  //   console.log(credential);
  // }
}
