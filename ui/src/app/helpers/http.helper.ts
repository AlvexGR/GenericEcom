import { HttpHeaders } from '@angular/common/http';

/**
 * Author: nhannn
 */
export class HttpHelper {
  static baseUrl = "http://localhost:8080/api/";
  static usersUrl = "users/";

  public static createHeader(authToken: string = ""): HttpHeaders {
    return new HttpHeaders({
      "Content-Type":  "application/json",
      "Accept": "application/json",
      "Authorization": authToken || ""
    });
  }
}
