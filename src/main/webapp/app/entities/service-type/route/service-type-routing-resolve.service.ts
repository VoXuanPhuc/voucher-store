import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServiceType, ServiceType } from '../service-type.model';
import { ServiceTypeService } from '../service/service-type.service';

@Injectable({ providedIn: 'root' })
export class ServiceTypeRoutingResolveService implements Resolve<IServiceType> {
  constructor(protected service: ServiceTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((serviceType: HttpResponse<ServiceType>) => {
          if (serviceType.body) {
            return of(serviceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceType());
  }
}
