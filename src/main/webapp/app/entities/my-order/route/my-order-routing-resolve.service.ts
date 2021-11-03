import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMyOrder, MyOrder } from '../my-order.model';
import { MyOrderService } from '../service/my-order.service';

@Injectable({ providedIn: 'root' })
export class MyOrderRoutingResolveService implements Resolve<IMyOrder> {
  constructor(protected service: MyOrderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMyOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((myOrder: HttpResponse<MyOrder>) => {
          if (myOrder.body) {
            return of(myOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MyOrder());
  }
}
