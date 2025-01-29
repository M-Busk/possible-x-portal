import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, catchError, throwError} from 'rxjs';
import {Router} from "@angular/router";
import {AuthService} from "../services/mgmt/auth/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private auth: AuthService)  {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // basic authentification for /registration/** path except POST /registration/request
    if (req.url.includes("/registration/") && !(req.url.match("\/registration\/request$") && req.method === "POST")) {
      var authToken = this.auth.getToken();
      if (authToken) {
        req = req.clone({
          setHeaders: {
            Authorization: `Basic ${authToken}`
          }
        });
      }
    }
    return next.handle(req);
  }
}
