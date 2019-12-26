import { BaseRequest } from "../base.request";
import { User } from "../../user.model";

/**
 * Author: nhannn
 */
export class SignUpRequest extends BaseRequest {
  user: User;

  constructor(user: User) {
    super();
    this.user = user;
  }
}
