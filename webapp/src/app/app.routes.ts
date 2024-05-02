import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'kbn',
    loadChildren: () => import('./kbn/kbn.module').then(m => m.KbnModule)
  }
];
