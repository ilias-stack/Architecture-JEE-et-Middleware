import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-new-product',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './new-product.component.html',
  styleUrl: './new-product.component.css',
})
export class NewProductComponent implements OnInit {
  saveProduct() {
    const product = this.productForm.value;
    this.service.saveProduct(product).subscribe({
      next: (value) => {
        this.productForm.reset();
      },
      error: (err) => console.log,
    });
  }
  public productForm!: FormGroup;

  constructor(private fb: FormBuilder, private service: ProductService) {}

  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: this.fb.control('', [Validators.required]),
      price: this.fb.control(0, [Validators.min(1)]),
      checked: this.fb.control(false),
    });
  }
}
