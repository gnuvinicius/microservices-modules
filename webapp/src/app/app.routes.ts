import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';

export const routes: Routes = [
  {
    path: '/webapp/auth',
    component: LoginComponent
  },
  {
    path: '/webapp/signup',
    component: SignupComponent
  }
];
