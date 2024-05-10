import { Component, inject } from '@angular/core';
import { InputComponent } from '../../components/input/input.component';
import { RouterModule } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    InputComponent,
    ReactiveFormsModule,
    RouterModule,
    CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  service = inject(AuthService);
  loginForm: FormGroup;
  showAlert = false;

  constructor() {
    this.loginForm = new FormGroup({
      login: new FormControl(''),
      password: new FormControl('')
    })
  }

  submit() {
    const payload = {
      login: this.loginForm.controls['login'].value,
      password: this.loginForm.controls['password'].value
    }
    this.service.login(payload.login, payload.password)
      // .subscribe(resp => {
      //   if (resp.status = 200) {
      //     console.log(resp);
      //     this.showAlert = true;
      //   } else {
      //     console.log(resp)
      //   }
      // })
  }

}
