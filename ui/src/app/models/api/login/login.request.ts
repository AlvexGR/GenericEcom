import { BaseRequest } from '../base.request';

/**
 * Author: nhannn
 */
export class LoginRequest extends BaseRequest {
  email: string;
  password: string;
  rememberMe: boolean;

  constructor(email: string, password: string, rememberMe: boolean) {
    super();

    this.email = email;
    this.password = password;
    this.rememberMe = rememberMe;
  }
}
