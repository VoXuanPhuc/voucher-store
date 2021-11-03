import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MyUserComponent } from '../list/my-user.component';
import { MyUserDetailComponent } from '../detail/my-user-detail.component';
import { MyUserUpdateComponent } from '../update/my-user-update.component';
import { MyUserRoutingResolveService } from './my-user-routing-resolve.service';

const myUserRoute: Routes = [
  {
    path: '',
    component: MyUserComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MyUserDetailComponent,
    resolve: {
      myUser: MyUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MyUserUpdateComponent,
    resolve: {
      myUser: MyUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MyUserUpdateComponent,
    resolve: {
      myUser: MyUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(myUserRoute)],
  exports: [RouterModule],
})
export class MyUserRoutingModule {}
