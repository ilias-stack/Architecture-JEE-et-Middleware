import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product.model';
import { FormsModule } from '@angular/forms';
import { error } from 'console';
import { AppStateService } from '../services/app-state.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
})
export class ProductsComponent implements OnInit {
  handleEditProduct(productIndex: number) {
    const currentProduct = this.appState.productsState.products[productIndex];

    console.log(productIndex);
    this.service.updateProduct(currentProduct).subscribe({
      next: (upProduct) => {
        console.log('Response from server:', upProduct);
        currentProduct.name = (upProduct as Product).name;
        currentProduct.price = (upProduct as Product).price;
      },
      error: (error) => console.log,
    });
    this.appState.productsState.editToggleIndex = -1;
  }

  toggleEditProduct(i: number) {
    this.appState.productsState.editToggleIndex = i;
  }

  handleGotoPage(page: number) {
    this.appState.productsState.currentPage = page;
    this.getProducts();
  }

  handleDeleteProduct(product: Product) {
    this.service.deleteProduct(product).subscribe({
      next: (value) => {
        this.appState.productsState.products =
          this.appState.productsState.products.filter(
            (p) => p.id != product.id
          );
      },
    });
  }
  constructor(
    private service: ProductService,
    public appState: AppStateService
  ) {}

  getProducts() {
    this.appState.setProductState({ status: 'LOADING' });
    this.service
      .getProducts(
        this.appState.productsState.keyword,
        this.appState.productsState.currentPage,
        this.appState.productsState.PAGE_SIZE
      )
      .subscribe({
        next: (resp) => {
          this.appState.productsState.products = resp.body as Product[];
          const totalProducts: number = parseInt(
            resp.headers.get('X-Total-Count')!
          );
          this.appState.productsState.totalProducts = totalProducts;
          this.appState.productsState.totalPages = isNaN(totalProducts)
            ? 0
            : Math.ceil(totalProducts / this.appState.productsState.PAGE_SIZE);
          this.appState.setProductState({ status: 'LOADED' });
        },
        error: (err) => {
          this.appState.setProductState({
            status: 'ERROR',
            errroMessage: err.message,
          });
        },
      });
  }

  ngOnInit(): void {
    this.getProducts();
  }

  handleCheckProduct(product: Product) {
    this.service.checkProduct(product).subscribe({
      next: (upProduct) => (product.checked = (upProduct as any).checked),
    });
  }
}
