import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherStatus, VoucherStatus } from '../voucher-status.model';
import { VoucherStatusService } from '../service/voucher-status.service';

@Injectable({ providedIn: 'root' })
export class VoucherStatusRoutingResolveService implements Resolve<IVoucherStatus> {
  constructor(protected service: VoucherStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoucherStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((voucherStatus: HttpResponse<VoucherStatus>) => {
          if (voucherStatus.body) {
            return of(voucherStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoucherStatus());
  }
}
