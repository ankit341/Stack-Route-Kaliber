import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  CanDeactivate
} from '@angular/router';
import { Observable } from 'rxjs';

export interface ConfirmComponent {
  confirm();
}

@Injectable({
  providedIn: 'root'
})
export class ConfirmationGuard implements CanDeactivate<ConfirmComponent> {
  canDeactivate(
    component: ConfirmComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    return component.confirm();
  }
}
