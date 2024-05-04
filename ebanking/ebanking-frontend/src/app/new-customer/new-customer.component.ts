import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../models/customer.model";
import {CustomerService} from "../services/customer.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit{

   newCustomerFormGroup!:FormGroup;

  constructor(private fb:FormBuilder,private customerService:CustomerService,private router:Router) {
  }
  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name:this.fb.control("",[Validators.required,Validators.minLength(4)]),
      email:this.fb.control("",[Validators.required,Validators.email])
    })
  }


  handleSaveCustomer() {
    let conf = confirm("Do you really want to delete this customer ?");
    if(!conf)return;

    const customer:Customer = this.newCustomerFormGroup.value;

    this.customerService.saveCustomer(customer).subscribe({
      next:value => {
        alert("Customer saved!");
        // this.newCustomerFormGroup.reset();
        this.router.navigateByUrl("/customers");
      },
      error:error=>
        console.log(error.message)

    })

  }
}
