import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {RegisterComponent} from './features/auth/register/register.component';
import {LoginComponent} from './features/auth/login/login.component';
import {HomeComponent} from './features/home/home.component';
import {ProfileComponent} from './features/auth/profile/profile.component';
import {ProductCreateComponent} from "./features/product/product-create/product-create.component";

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'product', component: ProductCreateComponent },
  { path: 'product/:id', component: ProductCreateComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
