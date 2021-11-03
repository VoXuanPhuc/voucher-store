import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VoucherStatusComponent } from '../list/voucher-status.component';
import { VoucherStatusDetailComponent } from '../detail/voucher-status-detail.component';
import { VoucherStatusUpdateComponent } from '../update/voucher-status-update.component';
import { VoucherStatusRoutingResolveService } from './voucher-status-routing-resolve.service';

const voucherStatusRoute: Routes = [
  {
    path: '',
    component: VoucherStatusComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoucherStatusDetailComponent,
    resolve: {
      voucherStatus: VoucherStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoucherStatusUpdateComponent,
    resolve: {
      voucherStatus: VoucherStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoucherStatusUpdateComponent,
    resolve: {
      voucherStatus: VoucherStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(voucherStatusRoute)],
  exports: [RouterModule],
})
export class VoucherStatusRoutingModule {}
