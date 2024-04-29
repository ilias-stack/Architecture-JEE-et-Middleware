import { Component } from '@angular/core';
import { AppStateService } from '../services/app-state.service';
import { LoadingService } from '../services/loading.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  login() {
    this.router.navigateByUrl('/login');
  }

  logout() {
    this.appState.authState = {};
    this.router.navigateByUrl('/login');
  }

  setCurrentAction(action: any) {
    this.currentAction = action;
  }

  isLoading!: boolean;

  constructor(
    public appState: AppStateService,
    public loadingService: LoadingService,
    private router: Router
  ) {}

  actions: Array<any> = [
    { title: 'Home', route: '/home', icon: 'house' },
    { title: 'Products', route: '/admin/products', icon: 'search' },
    { title: 'New Product', route: '/admin/newProduct', icon: 'safe' },
  ];

  currentAction: any;
}
