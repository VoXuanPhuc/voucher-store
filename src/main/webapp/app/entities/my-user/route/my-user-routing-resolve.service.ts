import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMyUser, MyUser } from '../my-user.model';
import { MyUserService } from '../service/my-user.service';

@Injectable({ providedIn: 'root' })
export class MyUserRoutingResolveService implements Resolve<IMyUser> {
  constructor(protected service: MyUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMyUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((myUser: HttpResponse<MyUser>) => {
          if (myUser.body) {
            return of(myUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MyUser());
  }
}
