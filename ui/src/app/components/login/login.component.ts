import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/services/user-service/user.service";
import { LoginResponse } from "src/app/models/api/login/login.response";
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl
} from "@angular/forms";
import { ActivatedRoute } from "@angular/router";

/**
 * Author: nhannn
 */
@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  waitingForResponse = false;
  returnUrl: string;
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute
  ) {}

  get email(): AbstractControl {
    return this.loginForm.get("email");
  }

  get password(): AbstractControl {
    return this.loginForm.get("password");
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required]
    });
    this.returnUrl = this.route.snapshot.queryParamMap.get("returnUrl") || "/";
    this.waitingForResponse = false;
  }

  async login(): Promise<void> {
    this.waitingForResponse = true;
    const loginResponse = await this.userService.login(
      this.email.value,
      this.password.value
    );
    console.log(loginResponse);
    this.waitingForResponse = false;
  }
}
