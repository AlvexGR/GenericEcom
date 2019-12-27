import { Component, OnInit, OnDestroy } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  AbstractControl,
  Validators
} from "@angular/forms";
import { UserService } from "src/app/services/user-service/user.service";
import { Router } from "@angular/router";
import { User } from "src/app/models/user.model";
import { UserStatus } from "src/app/helpers/enums/user-status.enum";
import { Role } from "src/app/helpers/enums/role.enum";
import { errorMessage, ErrorCode } from "src/app/helpers/enums/error-code.enum";
import { filter } from "rxjs/operators";
import { Subscription } from "rxjs";

/**
 * Author: nhannn
 */
@Component({
  selector: "app-sign-up",
  templateUrl: "./sign-up.component.html",
  styleUrls: ["./sign-up.component.css"]
})
export class SignUpComponent implements OnInit, OnDestroy {
  signUpForm: FormGroup;
  waitingForResponse: boolean;
  subscriptions: Subscription[];
  errors: any = {
    generalError: null,
    emailError: null,
    phoneNumberError: null,
    firstNameError: null,
    lastNameError: null,
    passwordError: null,
    PasswordConfirmError: null
  };
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signUpForm = this.formBuilder.group({
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      email: ["", [Validators.required, Validators.email]],
      password: [
        "",
        [
          Validators.required,
          Validators.pattern("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")
        ]
      ],
      passwordConfirm: ["", Validators.required],
      phoneNumber: ["", [Validators.required, Validators.pattern("^[0-9]*$")]],
      gender: [""],
      dateOfBirth: [""]
    });
    this.waitingForResponse = false;
    this.setUpFormControlValidationSubscriptions();
  }

  /**
   * Set up subscription for form validation
   */
  setUpFormControlValidationSubscriptions(): void {
    this.subscriptions = [
      this.firstName.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.firstNameError = null;
          } else {
            this.handleError(ErrorCode.FirstNameEmpty);
          }
        }),
      this.lastName.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.lastNameError = null;
          } else {
            this.handleError(ErrorCode.LastNameEmpty);
          }
        }),
      this.email.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.emailError = null;
          } else {
            this.handleError(
              this.email.value === ""
                ? ErrorCode.EmailEmpty
                : ErrorCode.EmailNotValid
            );
          }
        }),
      this.phoneNumber.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.phoneNumberError = null;
          } else {
            this.handleError(
              this.phoneNumber.value === ""
                ? ErrorCode.PhoneNumberEmpty
                : ErrorCode.PhoneNumberNotValid
            );
          }
        }),
      this.password.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.passwordError = null;
          } else {
            this.handleError(
              this.password.value === ""
                ? ErrorCode.PasswordEmpty
                : ErrorCode.PasswordConventionFailed
            );
          }
        }),
      this.passwordConfirm.valueChanges.subscribe(pwd => {
        if (pwd === "") {
          this.handleError(ErrorCode.PasswordConfirmEmpty);
        } else if (pwd !== this.password.value) {
          this.handleError(ErrorCode.PasswordNotMatched);
        } else {
          this.errors.passwordConfirmError = null;
        }
      })
    ];
  }

  ngOnDestroy(): void {
    // Unsubscribe to release resources
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  /**
   * Call sign up to service
   */
  async signUp(): Promise<void> {
    this.resetErrorMessage();
    this.waitingForResponse = true;
    const response = await this.userService.signUp(
      new User(
        null,
        this.firstName.value,
        this.lastName.value,
        this.email.value,
        this.password.value,
        this.phoneNumber.value,
        null,
        this.dateOfBirth.value,
        this.gender.value,
        UserStatus.Deactivated,
        Role.Customer,
        null,
        new Date(),
        null,
        null
      )
    );
    this.waitingForResponse = false;
    const result = this.handleError(response ? response.errorCode : null, true);
    if (result) {
      this.router.navigate(["/sign-up-success"]);
    }
  }

  /**
   * Reset all the error strings
   */
  resetErrorMessage() {
    this.errors.generalError = null;
    this.errors.emailError = null;
    this.errors.phoneNumberError = null;
    this.errors.lastNameError = null;
    this.errors.firstNameError = null;
    this.errors.passwordError = null;
    this.errors.PasswordConfirmError = null;
  }

  /**
   * Display errors to the UI
   * @param errorCode errorCode to define error
   * @param reset should reset all the message
   */
  handleError(errorCode: ErrorCode, reset: boolean = false): boolean {
    if (reset) {
      this.resetErrorMessage();
    }
    if (!errorCode) {
      this.errors.generalError = errorMessage.INTERNAL_ERROR;
      return false;
    }
    if (errorCode !== ErrorCode.Success) {
      switch (errorCode) {
        case ErrorCode.FirstNameEmpty:
          this.errors.firstNameError = errorMessage.FIRST_NAME_EMPTY;
          break;
        case ErrorCode.LastNameEmpty:
          this.errors.lastNameError = errorMessage.LAST_NAME_EMPTY;
          break;
        case ErrorCode.EmailEmpty:
          this.errors.emailError = errorMessage.EMAIL_EMPTY;
          break;
        case ErrorCode.EmailNotValid:
          this.errors.emailError = errorMessage.EMAIL_NOT_VALID;
          break;
        case ErrorCode.EmailExisted:
          this.errors.emailError = errorMessage.EMAIL_EXISTED;
          break;
        case ErrorCode.PhoneNumberEmpty:
          this.errors.phoneNumberError = errorMessage.PHONE_NUMBER_EMPTY;
          break;
        case ErrorCode.PhoneNumberNotValid:
          this.errors.phoneNumberError = errorMessage.PHONE_NUMBER_NOT_VALID;
          break;
        case ErrorCode.PhoneNumberExisted:
          this.errors.phoneNumberError = errorMessage.PHONE_NUMBER_EXISTED;
          break;
        case ErrorCode.PasswordEmpty:
          this.errors.passwordError = errorMessage.PASSWORD_EMPTY;
          break;
        case ErrorCode.PasswordConventionFailed:
          this.errors.passwordError = errorMessage.PASSWORD_CONVENTION_FAILED;
          break;
        case ErrorCode.PasswordConfirmEmpty:
          this.errors.passwordConfirmError =
            errorMessage.PASSWORD_CONFIRM_EMPTY;
          break;
        case ErrorCode.PasswordNotMatched:
          this.errors.passwordConfirmError = errorMessage.PASSWORD_NOT_MATCHED;
          break;
        case ErrorCode.InternalError:
          this.errors.generalError = errorMessage.INTERNAL_ERROR;
          break;
      }
      return false;
    }
    return true;
  }

  get password(): AbstractControl {
    return this.signUpForm.get("password");
  }

  get passwordConfirm(): AbstractControl {
    return this.signUpForm.get("passwordConfirm");
  }

  get email(): AbstractControl {
    return this.signUpForm.get("email");
  }

  get firstName(): AbstractControl {
    return this.signUpForm.get("firstName");
  }

  get lastName(): AbstractControl {
    return this.signUpForm.get("lastName");
  }

  get phoneNumber(): AbstractControl {
    return this.signUpForm.get("phoneNumber");
  }

  get gender(): AbstractControl {
    return this.signUpForm.get("gender");
  }

  get dateOfBirth(): AbstractControl {
    return this.signUpForm.get("dateOfBirth");
  }
}
