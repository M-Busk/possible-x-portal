import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, catchError, throwError} from 'rxjs';
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router)  {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // basic authentification for /registration/** path except POST /registration/request
    if (req.url.includes("/registration/") && !(req.url.match("\/registration\/request$") && req.method === "POST")) {
      var authToken = sessionStorage.getItem('authToken');
      if (!authToken) {
        var username = prompt("Please enter your username");
        var password = prompt("Please enter your password");
        var authToken = btoa(`${username}:${password}`);
        sessionStorage.setItem('authToken', authToken);
      }
      req = req.clone({
        setHeaders: {
          Authorization: `Basic ${authToken}`
        }
      });
      return next.handle(req).pipe(catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            sessionStorage.removeItem('authToken');
            console.log(error)
            alert('Invalid Token generated. Please log in again.');
          } else if (error.status === 403) {
            sessionStorage.removeItem('authToken');
            console.log(error)
            alert('Unauthorized Token used. Please log in with the correct Role.');
          } else {
            sessionStorage.removeItem('authToken');
            console.log(error)
            alert('Something went wrong.');
          }
          this.router.navigate(["/"]);
          return throwError(() => new Error(error.message));
          }));
    } else {
      return next.handle(req);
    }
  }
}
