import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../models/customer.model";
import {CustomerService} from "../services/customer.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {BankAccount} from "../models/account.model";
import {AccountsService} from "../services/accounts.service";
import {domainToASCII} from "url";
import {Data} from "../models/data";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrl: './customer-accounts.component.css'
})
export class CustomerAccountsComponent implements OnInit{
  customer!:Customer;
  customerId!:number;
  errorMessage!:string;

  accounts!:BankAccount[];

  constructor(private bankService:AccountsService,private route:ActivatedRoute,private router:Router,private customreService:CustomerService) {
  }

  ngOnInit(): void {
    this.customerId=this.route.snapshot.params["id"]
    this.customer = this.router.getCurrentNavigation()?.extras.state as Customer
    if(!this.customer){
      this.customreService.getCustomer(this.customerId).subscribe({
        next:data=>{
          this.customer=data;
          this.bankService.getCustomerAccounts(data!.id).subscribe({
            next:data=>{
              this.accounts=data;
            },
            error:error=>{
              this.errorMessage = error.message;
            }
          })
        },
        error:error=>{
          this.errorMessage = error.message;
        }
      })
    }

  }

  gotoOperations(id: string) {
    let data: Data = { id: id } ;
    this.router.navigateByUrl("/admin/accounts",{state:data})
  }
}
