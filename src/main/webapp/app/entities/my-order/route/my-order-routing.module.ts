import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MyOrderComponent } from '../list/my-order.component';
import { MyOrderDetailComponent } from '../detail/my-order-detail.component';
import { MyOrderUpdateComponent } from '../update/my-order-update.component';
import { MyOrderRoutingResolveService } from './my-order-routing-resolve.service';

const myOrderRoute: Routes = [
  {
    path: '',
    component: MyOrderComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MyOrderDetailComponent,
    resolve: {
      myOrder: MyOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MyOrderUpdateComponent,
    resolve: {
      myOrder: MyOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MyOrderUpdateComponent,
    resolve: {
      myOrder: MyOrderRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(myOrderRoute)],
  exports: [RouterModule],
})
export class MyOrderRoutingModule {}
