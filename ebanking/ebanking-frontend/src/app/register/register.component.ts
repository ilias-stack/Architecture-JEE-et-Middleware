import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  formRegister!: FormGroup;

  constructor(private fb:FormBuilder,private authService:AuthService,private router:Router) {
  }

  ngOnInit(): void {
    this.formRegister = this.fb.group({
      username: this.fb.control("", [Validators.required, Validators.minLength(3)]),
      email: this.fb.control("", [Validators.required, Validators.email]),
      password: this.fb.control("", [Validators.required, Validators.minLength(6)])
    });
  }

  handleRegister() {
    const { username, email, password } = this.formRegister.value;
    this.authService.register(username, email, password).subscribe(
      {
        next:response => {
          console.log('User registered successfully', response);
          this.authService.loadProfile(response);
          this.router.navigateByUrl("/admin/customers");
        },
        error:err => console.log(err)
      }
    );
  }


}
