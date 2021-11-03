import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VoucherCodeComponent } from '../list/voucher-code.component';
import { VoucherCodeDetailComponent } from '../detail/voucher-code-detail.component';
import { VoucherCodeUpdateComponent } from '../update/voucher-code-update.component';
import { VoucherCodeRoutingResolveService } from './voucher-code-routing-resolve.service';

const voucherCodeRoute: Routes = [
  {
    path: '',
    component: VoucherCodeComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoucherCodeDetailComponent,
    resolve: {
      voucherCode: VoucherCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoucherCodeUpdateComponent,
    resolve: {
      voucherCode: VoucherCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoucherCodeUpdateComponent,
    resolve: {
      voucherCode: VoucherCodeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(voucherCodeRoute)],
  exports: [RouterModule],
})
export class VoucherCodeRoutingModule {}
