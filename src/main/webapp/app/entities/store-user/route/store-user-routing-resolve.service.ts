import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStoreUser, StoreUser } from '../store-user.model';
import { StoreUserService } from '../service/store-user.service';

@Injectable({ providedIn: 'root' })
export class StoreUserRoutingResolveService implements Resolve<IStoreUser> {
  constructor(protected service: StoreUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStoreUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((storeUser: HttpResponse<StoreUser>) => {
          if (storeUser.body) {
            return of(storeUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StoreUser());
  }
}
