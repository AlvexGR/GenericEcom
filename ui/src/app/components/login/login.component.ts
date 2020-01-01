import { HttpHelper } from 'src/app/helpers/http.helper';
import { Component, OnInit, OnDestroy } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl
} from "@angular/forms";
import { ActivatedRoute } from "@angular/router";
import { Subscription } from 'rxjs';
import { ErrorCode, errorMessage } from 'src/app/helpers/enums/error-code.enum';
import { filter } from "rxjs/operators";
import { storageKey } from 'src/app/helpers/constant';
import { UserService } from 'src/app/services/user-service/user.service';
import { User } from 'src/app/models/user.model';

/**
 * Author: nhannn
 */
@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm: FormGroup;
  waitingForResponse = false;
  returnUrl: string;
  subscriptions: Subscription[];
  errors: any = {
    generalError: null,
    emailError: null,
    passwordError: null,
  };
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required],
      rememberMe: [false]
    });
    this.returnUrl = this.route.snapshot.queryParamMap.get("returnUrl") || "/";
    this.waitingForResponse = false;
    this.setUpFormControlValidationSubscriptions();
  }

  /**
   * Set up subscription for form validation
   */
  setUpFormControlValidationSubscriptions(): void {
    this.subscriptions = [
      this.email.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.emailError = null;
          } else {
            this.handleError(ErrorCode.EmailEmpty);
          }
        }),
      this.password.statusChanges
        .pipe(filter(status => status === "VALID" || status === "INVALID"))
        .subscribe(status => {
          if (status === "VALID") {
            this.errors.passwordError = null;
          } else {
            this.handleError(ErrorCode.PasswordEmpty);
          }
        }),
    ];
  }

  ngOnDestroy(): void {
    // Unsubscribe to release resources
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  /**
   * Perform login, send user's credentials to backend server to validate
   */
  async login(): Promise<void> {
    this.waitingForResponse = true;
    await HttpHelper.imitateResponseBehavior(3000);
    const loginResponse = await this.userService.login(
      this.email.value,
      this.password.value,
      this.rememberMe.value
    );
    if (loginResponse && loginResponse.user && loginResponse.jwtToken) {
      loginResponse.user.accessToken = loginResponse.jwtToken;
      localStorage.setItem(storageKey.CURRENT_USER, JSON.stringify(loginResponse.user));
    }
    this.waitingForResponse = false;
  }

  /**
   * Login as Google
   */
  async loginAsGoogle(): Promise<void> {
    await this.userService.loginAsGoogle();
  }

  /**
   * Reset all the error strings
   */
  resetErrorMessage() {
    this.errors.generalError = null;
    this.errors.emailError = null;
    this.errors.passwordError = null;
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
        case ErrorCode.EmailEmpty:
          this.errors.emailError = errorMessage.EMAIL_EMPTY;
          break;
        case ErrorCode.EmailExisted:
          this.errors.emailError = errorMessage.EMAIL_EXISTED;
          break;
        case ErrorCode.PasswordEmpty:
          this.errors.passwordError = errorMessage.PASSWORD_EMPTY;
          break;
        case ErrorCode.LoginFailed:
          this.errors.generalError = errorMessage.LOGIN_FAILED;
          break;
        case ErrorCode.InternalError:
          this.errors.generalError = errorMessage.INTERNAL_ERROR;
          break;
      }
      return false;
    }
    return true;
  }

  get email(): AbstractControl {
    return this.loginForm.get("email");
  }

  get password(): AbstractControl {
    return this.loginForm.get("password");
  }

  get rememberMe(): AbstractControl {
    return this.loginForm.get("rememberMe");
  }
}
