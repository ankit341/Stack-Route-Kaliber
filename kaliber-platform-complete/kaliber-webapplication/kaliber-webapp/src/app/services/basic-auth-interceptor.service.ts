import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpEvent, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';


@Injectable({
providedIn: 'root'
})
export class BasicAuthInterceptorService implements HttpInterceptor {
private token: string;
constructor(private router: Router) { }
 intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   return next.handle(request).pipe(
    catchError((err: any) => {
               if (err instanceof HttpErrorResponse) {
                   if (err.status === 403 || err.status === 401) {
                     this.router.navigate(['/']);
                   }
               }
               return of(err);
           })
   );
 }
}
