import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StoreUserComponent } from '../list/store-user.component';
import { StoreUserDetailComponent } from '../detail/store-user-detail.component';
import { StoreUserUpdateComponent } from '../update/store-user-update.component';
import { StoreUserRoutingResolveService } from './store-user-routing-resolve.service';

const storeUserRoute: Routes = [
  {
    path: '',
    component: StoreUserComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StoreUserDetailComponent,
    resolve: {
      storeUser: StoreUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StoreUserUpdateComponent,
    resolve: {
      storeUser: StoreUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StoreUserUpdateComponent,
    resolve: {
      storeUser: StoreUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(storeUserRoute)],
  exports: [RouterModule],
})
export class StoreUserRoutingModule {}
