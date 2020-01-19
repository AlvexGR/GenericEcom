/**
 * Author: nhannn
 */
export enum ErrorCode {
  Success = "SUCCESS",

  FirstNameEmpty = "FIRST_NAME_EMPTY",
  LastNameEmpty = "LAST_NAME_EMPTY",
  EmailEmpty = "EMAIL_EMPTY",
  EmailNotValid = "EMAIL_NOT_VALID",
  PhoneNumberEmpty = "PHONE_NUMBER_EMPTY",
  PhoneNumberNotValid = "PHONE_NUMBER_NOT_VALID",
  PasswordEmpty = "PASSWORD_EMPTY",
  PasswordConfirmEmpty = "PASSWORD_CONFIRM_EMPTY",
  AccountNotExisted = "ACCOUNT_NOT_EXISTED",
  EmailExisted = "EMAIL_EXISTED",
  PhoneNumberExisted = "PHONE_NUMBER_EXISTED",
  PasswordConventionFailed = "PASSWORD_CONVENTION_FAILED",
  PasswordNotMatched = "PASSWORD_NOT_MATCHED",
  LoginFailed = "LOGIN_FAILED",

  /* General errors */
  InternalError = "INTERNAL_ERROR",
  BadRequest = "BAD_REQUEST"
}


/**
 * Object name must match to ErrorCode enum string
 */
export const errorMessage = {
  SUCCESS: "Success",
  ACCOUNT_NOT_EXISTED: "This account is not existed",
  EMAIL_NOT_VALID: "This email is not valid",
  EMAIL_EXISTED: "This email is already existed",
  PHONE_NUMBER_EXISTED: "This phone number is already existed",
  PHONE_NUMBER_NOT_VALID: "This phone number is not valid",
  PASSWORD_CONVENTION_FAILED: "Your password should contains at least 1 upper case (A-Z), 1 lower case (a-z), 1 number (0-9) and 8 characters",
  PASSWORD_NOT_MATCHED: "Password must match",
  FIRST_NAME_EMPTY: "Please enter your first name",
  LAST_NAME_EMPTY: "Please enter your last name",
  EMAIL_EMPTY: "Please enter your email",
  PHONE_NUMBER_EMPTY: "Please enter your phone number",
  PASSWORD_EMPTY: "Please enter your password",
  PASSWORD_CONFIRM_EMPTY: "Please enter your password confirmation",
  LOGIN_FAILED: "Login failed, please check your credentials",

  INTERNAL_ERROR: "Something went wrong. Please try again later",
  BAD_REQUEST: "Something went wrong. Please try again later"
};
