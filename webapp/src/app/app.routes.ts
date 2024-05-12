import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';

export const routes: Routes = [
  {
    path: 'auth-page',
    component: LoginComponent
  },
  {
    path: 'signup-page',
    component: SignupComponent
  }
];
