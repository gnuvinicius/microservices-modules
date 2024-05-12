import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { AuthService } from '../auth/auth.service';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KbnRoutingModule { }
