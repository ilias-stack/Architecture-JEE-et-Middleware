import { Component } from '@angular/core';
import { AppStateService } from '../services/app-state.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  constructor(public appState: AppStateService) {}
  totalCheckedProducts() {
    return this.appState.productsState.products.filter((p) => p.checked).length;
  }
}
