import {Component, OnInit} from '@angular/core';
import {CustomerService} from "../services/customer.service";
import {Customer} from "../models/customer.model";
import {catchError, map, Observable, throwError} from "rxjs";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {Data} from "../models/data";


@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit{
  customers!:Observable<Customer[]>;
  errorMessage:string|undefined;
  searchFormGroup!:FormGroup;
  constructor(private customerService:CustomerService,private fb:FormBuilder,private router:Router) {
  }
  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({keyword:this.fb.control("")});

    this.handleSearchCustomers()
  }

  handleSearchCustomers() {
    const keyword = this.searchFormGroup.value.keyword;

    this.customers = this.customerService.searchCustomers(keyword).pipe(catchError(err => {
      this.errorMessage=err.message;
      return throwError(err);
    }));
  }

  handleDeleteCustomer(id: number) {
    this.customerService.deleteCustomer(id).subscribe({
      next:value => {
        this.customers = this.customers.pipe(map(customers => customers.filter(customer => customer.id!=id)))
      },
      error:error=>console.log(error)

    })
  }

  handleCustomerAccounts(customer : Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.id,{
      state:customer
    })
  }
}
