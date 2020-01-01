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
  public async login(email: string, password: string, rememberMe: boolean = false): Promise<LoginResponse> {
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
   * Check if there is any user with this email on back end server
   * @param email toCheck
   */
  public async verifyEmailExistence(email: string): Promise<boolean> {
    const headers = HttpHelper.createHeader();
    const url = HttpHelper.baseUrl + HttpHelper.usersUrl + `verify/${email}`;
    try {
      const response = await this.http
        .get<boolean>(url, { headers })
        .toPromise();
      return response;
    } catch (err) {
      console.log(err);
      return null;
    }
  }

  /**
   * Google login
   */
  public async loginAsGoogle(): Promise<void> {
    try {
      const authUser = await this.fireAuth.auth.signInWithPopup(
        new auth.GoogleAuthProvider()
      );
      if (await this.verifyEmailExistence(authUser.user.email)) {
        // login
      } else {
        // register
      }
      console.log(authUser);
    } catch (err) {
      console.log(err);
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
