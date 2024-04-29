import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  handleLogin() {
    const username = this.formLogin.value.username;
    const password = this.formLogin.value.password;
    this.authService
      .login(username, password)
      .then((resp) => {
        this.router.navigateByUrl('/admin');
      })
      .catch((e) => {
        this.errorMessage = e;
      });
  }
  formLogin!: FormGroup;
  errorMessage: any = undefined;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.formLogin = this.fb.group({
      username: this.fb.control(''),
      password: this.fb.control(''),
    });
  }
}
