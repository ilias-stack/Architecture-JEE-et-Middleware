import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated = false;
  roles:any;
  username:string=""
  accessToken="";

  constructor(private http:HttpClient,private router:Router) { }

  public login(username:string,password:string){
    const options = {
      headers:new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    const params =
      new HttpParams().set("username",username).set("password",password)

    return this.http.post("http://localhost:8085/auth/login",params,options)
  }

  loadProfile(data: any) {
    this.isAuthenticated=true;
    this.accessToken = data["access-token"];
    const jwtDecoder:any = jwtDecode(this.accessToken);

    this.username = jwtDecoder.sub as string;
    this.roles = jwtDecoder.scope

    window.localStorage.setItem("jwt-token",this.accessToken);
  }

  logout() {
    this.isAuthenticated=false;
    this.accessToken="";
    this.username=""
    this.roles=undefined
    window.localStorage.removeItem("jwt-token");
    this.router.navigateByUrl("/login");

  }

  loadJwtTokenFromLocalStorage() {
    const token = window.localStorage.getItem("jwt-token")??"";
    if(token){
      this.loadProfile({
        "access-token":token
      });
      this.router.navigateByUrl("/admin/customers")
    }
  }
}
