import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn, GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";

@Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthorizationGuard implements CanActivate{

  constructor(private authService:AuthService,private router:Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    if(this.authService.roles.includes("ADMIN")) return true;
    this.router.navigateByUrl("/admin/notAuth")
    return false;
  }

}
