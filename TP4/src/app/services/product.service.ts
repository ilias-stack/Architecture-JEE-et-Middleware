import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private host: string = 'http://localhost:8089';

  saveProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.host + '/products', product);
  }
  constructor(private http: HttpClient) {}

  public getProducts(keyword: string = '', page: number = 1, size: number = 5) {
    return this.http.get<Array<Product>>(
      this.host + `/products?name_like=${keyword}&_page=${page}&_limit=${size}`,
      { observe: 'response' }
    );
  }

  public checkProduct(product: Product): Observable<Product> {
    return this.http.patch<Product>(this.host + '/products/' + product.id, {
      checked: !product.checked,
    });
  }

  public updateProduct(product: Product): Observable<Product> {
    return this.http.patch<Product>(
      this.host + '/products/' + product.id,
      product
    );
  }

  public deleteProduct(product: Product) {
    return this.http.delete(this.host + '/products/' + product.id);
  }

  public searchProducts(
    keyword: string,
    currentPage: number,
    pageSize: number
  ): Observable<Array<Product>> {
    return this.http.get<Array<Product>>(
      this.host +
        `/products?name_like=${keyword}&_page=${currentPage}&_limit=${pageSize}`
    );
  }
}
