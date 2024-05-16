import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {AuthService} from "../services/auth.service";

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  constructor(private authService:AuthService,) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.url.includes("/login") || req.url.includes("/auth/register")) return next.handle(req);

    const request = req.clone({
      headers: req.headers.set('Authorization','Bearer '+this.authService.accessToken)
    })
    return next.handle(request).pipe(catchError(err => {
      if(err.status==401)
        this.authService.logout();
      return throwError(err);
    }))
  }

}
