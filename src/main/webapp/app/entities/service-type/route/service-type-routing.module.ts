import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ServiceTypeComponent } from '../list/service-type.component';
import { ServiceTypeDetailComponent } from '../detail/service-type-detail.component';
import { ServiceTypeUpdateComponent } from '../update/service-type-update.component';
import { ServiceTypeRoutingResolveService } from './service-type-routing-resolve.service';

const serviceTypeRoute: Routes = [
  {
    path: '',
    component: ServiceTypeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceTypeDetailComponent,
    resolve: {
      serviceType: ServiceTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceTypeUpdateComponent,
    resolve: {
      serviceType: ServiceTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceTypeUpdateComponent,
    resolve: {
      serviceType: ServiceTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(serviceTypeRoute)],
  exports: [RouterModule],
})
export class ServiceTypeRoutingModule {}
