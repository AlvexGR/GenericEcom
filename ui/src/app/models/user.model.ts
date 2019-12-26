import { BaseModel } from "./base.model";
import { UserStatus } from "../helpers/enums/user-status.enum";
import { Role } from "../helpers/enums/role.enum";

/**
 * Author: nhannn
 */
export class User extends BaseModel {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
  address: string;
  dateOfBirth: string;
  gender: boolean;
  status: UserStatus;
  role: Role;
  image: any;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;

  accessToken: string;

  constructor(
    id: string,
    firstName: string,
    lastName: string,
    email: string,
    password: string,
    phoneNumber: string,
    address: string,
    dateOfBirth: string,
    gender: boolean,
    status: UserStatus,
    role: Role,
    image: any,
    createdAt: Date,
    updatedAt: Date,
    deletedAt: Date
  ) {
    super();

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.status = status;
    this.role = role;
    this.image = image;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }
}
