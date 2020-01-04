import { BaseRequest } from "./../base.request";

export class GoogleLoginRequest extends BaseRequest {
  email: string;
  firstName: string;
  lastName: string;
  phoneNubmer: string;
  accessToken: string;

  constructor(
    email: string,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    accessToken: string
  ) {
    super();

    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNubmer = phoneNumber;
    this.accessToken = accessToken;
  }
}
