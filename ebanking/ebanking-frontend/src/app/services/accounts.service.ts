import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountDetails, BankAccount} from "../models/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  backendHost:string = "http://localhost:8085";

  constructor(private http:HttpClient) { }

  public getAccount(accountId:string,page:number,size:number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(`${this.backendHost}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`)
  }

  public debit(accountId:string,amount:number,desc:string){
    return this.http.post(`${this.backendHost}/accounts/debit`,{
      accountId,amount,description:desc
    })
  }

  public credit(accountId:string,amount:number,desc:string){
    return this.http.post(`${this.backendHost}/accounts/credit`,{
      accountId,amount,description:desc
    })
  }

  public transfer(accountSource:string,accountDestination:string,amount:number,desc:string){
    return this.http.post(`${this.backendHost}/accounts/transfer`,{
      accountSource,accountDestination,amount,description:desc
    })
  }

  public getCustomerAccounts(customerId:number):Observable<BankAccount[]>{
    return this.http.get<BankAccount[]>(`${this.backendHost}/accounts/${customerId}/accounts`);
  }

}
