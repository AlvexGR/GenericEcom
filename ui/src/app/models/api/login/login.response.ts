import { BaseResponse } from '../base.response';
import { User } from '../../user.model';

/**
 * Author: nhannn
 */
export class LoginResponse extends BaseResponse {
  user: User;

  constructor(user: User) {
    super();

    this.user = user;
  }
}
