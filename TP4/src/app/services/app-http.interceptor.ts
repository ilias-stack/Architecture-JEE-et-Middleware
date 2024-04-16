import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { AppStateService } from './app-state.service';
import { finalize } from 'rxjs';
import { LoadingService } from './loading.service';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {
  constructor(
    public appState: AppStateService,
    public loadingService: LoadingService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // this.appState.setProductState({
    //   status: 'LOADING',
    // });
    this.loadingService.showLoadingSpinner();
    const request = req.clone({
      headers: req.headers.set('Authorization', 'Bearer JWT'),
    });
    return next.handle(request).pipe(
      finalize(() => {
        this.loadingService.hideLoadingSpinner();
      })
    );
  }
}
