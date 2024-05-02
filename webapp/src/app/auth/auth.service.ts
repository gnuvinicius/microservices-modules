import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  TOKEN_KEY = '@garage-dlvr-token';
  constructor(private router: Router) {}

  canActivate() {
    if (!this.isAuthenticated()) {
      this.router.navigate(['auth']);
      return false;
    }
    return true;
  }

  isAuthenticated(): boolean {
    return localStorage.getItem(this.TOKEN_KEY) !== null;
  }
}