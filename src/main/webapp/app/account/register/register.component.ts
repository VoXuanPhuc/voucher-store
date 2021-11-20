import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/config/error.constants';
import { RegisterService } from './register.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  dontMatchPassword = false;
  user?: IMyUser;

  inforUserSignup = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    phone: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254)]],
  });

  constructor(private formBuilder: FormBuilder, private myUserService: MyUserService) {}

  register(): void {
    const password = this.inforUserSignup.get(['password'])!.value;
    const confirmPassword = this.inforUserSignup.get(['confirmPassword'])!.value;
    if (password !== confirmPassword) {
      this.dontMatchPassword = true;
    } else {
      const username = this.inforUserSignup.get(['username'])!.value;
      const email = this.inforUserSignup.get(['email'])!.value;
      const phone = this.inforUserSignup.get(['phone'])!.value;
      const userObject = {
        username,
        password,
        firstName: '',
        lastName: '',
        gender: '',
        phone,
        email,
      };
      this.user = userObject;
      this.myUserService
        .create(this.user)
        .pipe()
        .subscribe(
          () => window.console.log('success'),
          () => window.console.log('faild')
        );
    }

    window.console.log(this.user);
  }
}
