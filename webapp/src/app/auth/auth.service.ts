import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  http = inject(HttpClient);
  header: HttpHeaders = new HttpHeaders();

  TOKEN_KEY = '@garage-dlvr-token';
  constructor(private router: Router) {
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
      // 'rejectUnauthorized': 'false',
    });  
  }

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

  login(email: string, password: string): void {
    this.http.post<HttpResponse<any>>(`${environment.apiUrl}/auth/api/v1/login`,
      { email, password }, { headers : this.header, observe: 'response' })
      .subscribe({
        next: (resp: HttpResponse<any>) => {
          if (resp.status == 200) {
            localStorage.setItem(this.TOKEN_KEY, resp.body.access_token);
          }
        },
        error: (err: HttpErrorResponse) => console.log(err)
      }
    )
  }

  signup(signup: any): Observable<any> {

    const payload = {
      nome: signup.titulo,
      cnpj: signup.cnpj,
      admin: {
        nome: signup.nome,
        email: signup.email,
        password: signup.password,
        admin: true
      }
    }

    return this.http.post(`${environment.apiUrl}/auth/api/v1/manager/empresas`, payload, 
      { headers : this.header, observe: 'response' })
  }
}