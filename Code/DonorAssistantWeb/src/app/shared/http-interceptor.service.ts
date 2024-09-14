import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {DonorAssistantApiService} from "./donor-assistant-api.service";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  constructor(private apiClient: DonorAssistantApiService) {
  }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const value = this.apiClient.getCredentials();
    const modifiedReq = req.clone({
      setHeaders: {
        'Authorization': `Basic ${value}`,
      },
    });

    return next.handle(modifiedReq);
  }
}
