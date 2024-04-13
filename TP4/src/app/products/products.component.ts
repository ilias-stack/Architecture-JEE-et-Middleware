import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product.model';
import { FormsModule } from '@angular/forms';
import { error } from 'console';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
})
export class ProductsComponent implements OnInit {
  editToggleIndex: number = -1;

  handleEditProduct(productIndex: number) {
    const currentProduct = this.products[productIndex];

    console.log(productIndex);
    this.service.updateProduct(currentProduct).subscribe({
      next: (upProduct) => {
        console.log('Response from server:', upProduct);
        currentProduct.name = (upProduct as Product).name;
        currentProduct.price = (upProduct as Product).price;
      },
      error: (error) => console.log,
    });
    this.editToggleIndex = -1;
  }

  toggleEditProduct(i: number) {
    this.editToggleIndex = i;
  }

  handleGotoPage(page: number) {
    this.currentPage = page;
    this.getProducts();
  }

  keyword: string = '';
  handleDeleteProduct(product: Product) {
    this.service.deleteProduct(product).subscribe({
      next: (value) => {
        this.products = this.products.filter((p) => p.id != product.id);
      },
    });
  }
  constructor(private service: ProductService) {}

  PAGE_SIZE: number = 5;
  currentPage: number = 1;
  totalPages: number = 0;

  getProducts() {
    this.service
      .getProducts(this.keyword, this.currentPage, this.PAGE_SIZE)
      .subscribe({
        next: (resp) => {
          this.products = resp.body as Product[];
          const totalProducts: number = parseInt(
            resp.headers.get('X-Total-Count')!
          );
          this.totalPages = isNaN(totalProducts)
            ? 0
            : Math.ceil(totalProducts / this.PAGE_SIZE);
        },
        error: (err) => {
          console.log('error');
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
  products: Array<Product> = [];
}
