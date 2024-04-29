import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class AppStateService {
  public productsState = {
    keyword: '',
    PAGE_SIZE: 5,
    currentPage: 1,
    totalPages: 0,
    products: [] as Product[],
    editToggleIndex: -1,
    totalProducts: 0,
    status: '',
    errorMessage: '',
  };

  public authState: any = {
    username: undefined,
    roles: undefined,
    isAuthenticated: false,
    token: undefined,
  };

  public setProductState(state: any): void {
    this.productsState = { ...this.productsState, ...state };
  }

  public setAuthState(state: any): void {
    this.authState = { ...this.authState, ...state };
  }

  constructor() {}
}
