import { Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';

@Injectable()
export class I1 implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const modified = req.clone({setHeaders: {'Content-Type': 'application/x-www-form-urlencoded'}});
    return next.handle(modified);
  }
}

@Injectable()
export class I2 implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const modified = req.clone({setHeaders: {'Authorization': 'Basic ' + btoa('OauthClient:OauthSuperSecret')}});
    return next.handle(modified);
  }
}
