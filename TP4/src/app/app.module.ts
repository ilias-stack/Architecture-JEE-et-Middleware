import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppErrorsComponent } from './app-errors/app-errors.component';
import { AppHttpInterceptor } from './services/app-http.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    NavbarComponent,
    AppErrorsComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, CommonModule, HttpClientModule],
  providers: [
    provideClientHydration(),
    ProductService,
    { provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
