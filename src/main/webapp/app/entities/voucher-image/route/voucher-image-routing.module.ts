import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VoucherImageComponent } from '../list/voucher-image.component';
import { VoucherImageDetailComponent } from '../detail/voucher-image-detail.component';
import { VoucherImageUpdateComponent } from '../update/voucher-image-update.component';
import { VoucherImageRoutingResolveService } from './voucher-image-routing-resolve.service';

const voucherImageRoute: Routes = [
  {
    path: '',
    component: VoucherImageComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoucherImageDetailComponent,
    resolve: {
      voucherImage: VoucherImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoucherImageUpdateComponent,
    resolve: {
      voucherImage: VoucherImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoucherImageUpdateComponent,
    resolve: {
      voucherImage: VoucherImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(voucherImageRoute)],
  exports: [RouterModule],
})
export class VoucherImageRoutingModule {}
