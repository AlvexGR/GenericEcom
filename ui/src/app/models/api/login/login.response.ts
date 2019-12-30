import { BaseResponse } from '../base.response';
import { User } from '../../user.model';

/**
 * Author: nhannn
 */
export class LoginResponse extends BaseResponse {
  user: User;
  jwtToken: string;

  constructor(user: User, jwtToken: string) {
    super();

    this.user = user;
    this.jwtToken = jwtToken;
  }
}
