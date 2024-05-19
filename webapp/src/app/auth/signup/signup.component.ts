import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../auth.service';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    NgxMaskDirective, 
    NgxMaskPipe, 
    RouterModule,
    ReactiveFormsModule
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  authService = inject(AuthService);
  signupForm: FormGroup; 

  constructor(private router: Router) {
    this.signupForm = new FormGroup({
      titulo: new FormControl(''),
      cnpj: new FormControl(''),
      nome: new FormControl(''),
      email: new FormControl(''),
      password: new FormControl('')
    });
  }

  submit() {
    const signup = {
      titulo: this.signupForm.controls['titulo'].value,
      cnpj: this.signupForm.controls['cnpj'].value,
      email: this.signupForm.controls['email'].value,
      nome: this.signupForm.controls['nome'].value,
      password: this.signupForm.controls['password'].value
    }
    this.authService.signup(signup).subscribe(resp => {
      console.log(resp);
      if (resp.status == 201) this.router.navigate(['/auth'])
    } 
    );

  }
}
