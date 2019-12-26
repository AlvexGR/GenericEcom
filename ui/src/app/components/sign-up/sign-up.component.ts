import { Component, OnInit } from "@angular/core";
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

/**
 * Author: nhannn
 */
@Component({
  selector: "app-sign-up",
  templateUrl: "./sign-up.component.html",
  styleUrls: ["./sign-up.component.css"]
})
export class SignUpComponent implements OnInit {
  signUpForm: FormGroup;
  waitingForResponse: boolean;
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.signUpForm = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required]],
      passwordConfirm: ["", Validators.required],
      firstName: ["", Validators.required],
      lastName: ["", Validators.required],
      phoneNumber: ["", Validators.required],
      gender: [""],
      dateOfBirth: [""]
    });
    this.waitingForResponse = false;
  }

  async signUp(): Promise<void> {
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
    console.log(response);
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
    return this.signUpForm.get("lastName");
  }

  get gender(): AbstractControl {
    return this.signUpForm.get("gender");
  }

  get dateOfBirth(): AbstractControl {
    return this.signUpForm.get("dateOfBirth");
  }
}
