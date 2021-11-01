import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherCode, VoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';

@Injectable({ providedIn: 'root' })
export class VoucherCodeRoutingResolveService implements Resolve<IVoucherCode> {
  constructor(protected service: VoucherCodeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoucherCode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((voucherCode: HttpResponse<VoucherCode>) => {
          if (voucherCode.body) {
            return of(voucherCode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoucherCode());
  }
}
