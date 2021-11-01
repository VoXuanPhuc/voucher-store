import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBenifitPackage, BenifitPackage } from '../benifit-package.model';
import { BenifitPackageService } from '../service/benifit-package.service';

@Injectable({ providedIn: 'root' })
export class BenifitPackageRoutingResolveService implements Resolve<IBenifitPackage> {
  constructor(protected service: BenifitPackageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBenifitPackage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((benifitPackage: HttpResponse<BenifitPackage>) => {
          if (benifitPackage.body) {
            return of(benifitPackage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BenifitPackage());
  }
}
