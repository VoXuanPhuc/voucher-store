import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVoucherImage, VoucherImage } from '../voucher-image.model';
import { VoucherImageService } from '../service/voucher-image.service';

@Injectable({ providedIn: 'root' })
export class VoucherImageRoutingResolveService implements Resolve<IVoucherImage> {
  constructor(protected service: VoucherImageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoucherImage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((voucherImage: HttpResponse<VoucherImage>) => {
          if (voucherImage.body) {
            return of(voucherImage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VoucherImage());
  }
}
