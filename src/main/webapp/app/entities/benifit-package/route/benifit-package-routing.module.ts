import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BenifitPackageComponent } from '../list/benifit-package.component';
import { BenifitPackageDetailComponent } from '../detail/benifit-package-detail.component';
import { BenifitPackageUpdateComponent } from '../update/benifit-package-update.component';
import { BenifitPackageRoutingResolveService } from './benifit-package-routing-resolve.service';

const benifitPackageRoute: Routes = [
  {
    path: '',
    component: BenifitPackageComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BenifitPackageDetailComponent,
    resolve: {
      benifitPackage: BenifitPackageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BenifitPackageUpdateComponent,
    resolve: {
      benifitPackage: BenifitPackageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BenifitPackageUpdateComponent,
    resolve: {
      benifitPackage: BenifitPackageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(benifitPackageRoute)],
  exports: [RouterModule],
})
export class BenifitPackageRoutingModule {}
