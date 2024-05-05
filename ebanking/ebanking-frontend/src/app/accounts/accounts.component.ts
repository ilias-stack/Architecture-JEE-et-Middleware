import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountsService} from "../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../models/account.model";
import {ActivatedRoute, Router} from "@angular/router";
import {Data} from "../models/data";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit{
  accountFormGroup!:FormGroup;
  operationFormGroup!:FormGroup;
  currentPage:number=0;
  pageSize:number=5;
  account$!:Observable<AccountDetails>;
  errorMessage!:string;
  state!:Data;
  constructor(private fb:FormBuilder,private accountService:AccountsService,private router:Router,private route:ActivatedRoute) {
     this.state = this.router.getCurrentNavigation()?.extras.state as Data;
  }

  ngOnInit(): void {

    this.accountFormGroup = this.fb.group({
      accountId:this.fb.control(this.state.id ?? "",[Validators.required])
    });

    this.operationFormGroup = this.fb.group({
      accountDestination:this.fb.control("",[Validators.required]),
      type:this.fb.control("",[Validators.required]),
      amount:this.fb.control(0,[Validators.required]),
      description:this.fb.control("",[Validators.required]),
    });
  }

  handleSearchAccount() {
    const accId = this.accountFormGroup.value.accountId;
    this.account$ = this.accountService.getAccount(accId,this.currentPage,this.pageSize).pipe(catchError(err => {
      this.errorMessage = err.message;
      return throwError(err);
    }));
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    const accId = this.accountFormGroup.value.accountId;
    const opType = this.accountFormGroup.value.type;
    if(opType=="DEBIT"){
      this.accountService.debit(accId,this.operationFormGroup.value.amount,this.operationFormGroup.value.description).subscribe({
        next:value => {
          alert("Debit operation is successful")
        },error:err => console.log(err)
      })
    }else if(opType=="CREDIT"){
      this.accountService.credit(accId,this.operationFormGroup.value.amount,this.operationFormGroup.value.description).subscribe({
        next:value => {
          alert("Credit operation is successful")
        },error:err => console.log(err)
      })
    }
    else if(opType=="TRANSFER"){
      this.accountService.transfer(accId,this.operationFormGroup.value.accountDestination,this.operationFormGroup.value.amount,this.operationFormGroup.value.description).subscribe({
        next:value => {
          alert("Transfer operation is successful")
        },error:err => console.log(err)
      })
    }
    this.handleSearchAccount();
    this.operationFormGroup.reset();
  }
}
