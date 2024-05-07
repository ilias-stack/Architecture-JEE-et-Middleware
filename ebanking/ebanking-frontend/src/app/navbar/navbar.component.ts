import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent{
  constructor(public router: Router, private cdr: ChangeDetectorRef,public authService:AuthService) {}

  isRouteActive(route: string): boolean {
    return this.router.url.includes(route);
  }

  navigateTo(route: string): void {
    this.router.navigateByUrl(route).then(() => {
      this.cdr.detectChanges();
    });
  }

  handleLogout() {
    this.authService.logout()
  }
}
