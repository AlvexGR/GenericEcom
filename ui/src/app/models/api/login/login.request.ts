import { BaseRequest } from '../base.request';

/**
 * Author: nhannn
 */
export class LoginRequest extends BaseRequest {
  email: string;
  password: string;

  constructor(email: string, password: string) {
    super();

    this.email = email;
    this.password = password;
  }
}
