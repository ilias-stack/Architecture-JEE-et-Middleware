import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../models/customer.model";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost:string = "http://localhost:8085";

  constructor(private http:HttpClient) { }

  public getCustomers():Observable<Customer[]>{
    return this.http.get<Customer[]>(this.backendHost+"/customers")
  }

  public getCustomer(id:number):Observable<Customer>{
    return this.http.get<Customer>(this.backendHost+"/customers/"+id);
  }

  public searchCustomers(keyword:string):Observable<Customer[]>{
    return this.http.get<Customer[]>(this.backendHost+"/customers/search?keyword="+keyword)
  }

  public saveCustomer(customer:Customer):Observable<Customer>{
    return this.http.post<Customer>(this.backendHost+"/customers",customer)
  }

  public deleteCustomer(customerId:number):Observable<void>{
    return this.http.delete<void>(this.backendHost+"/customers/"+customerId);
  }



}
