import { HttpHeaders } from '@angular/common/http';

/**
 * Author: nhannn
 */
export class HttpHelper {
  static baseUrl = "http://localhost:8080/api/";
  static usersUrl = "users/";

  /**
   * Create approriate header for all requests
   * @param accessToken for Authorization header
   */
  public static createHeader(accessToken: string = ""): HttpHeaders {
    return new HttpHeaders({
      "Content-Type":  "application/json",
      "Accept": "application/json",
      "Authorization": accessToken || ""
    });
  }

  /**
   * Return a promise to await, act like waiting for server to response
   * @param waitTime time to wait in milliseconds
   */
  public static imitateResponseBehavior(waitTime: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, waitTime));
  }
}
