import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeedbackImageComponent } from '../list/feedback-image.component';
import { FeedbackImageDetailComponent } from '../detail/feedback-image-detail.component';
import { FeedbackImageUpdateComponent } from '../update/feedback-image-update.component';
import { FeedbackImageRoutingResolveService } from './feedback-image-routing-resolve.service';

const feedbackImageRoute: Routes = [
  {
    path: '',
    component: FeedbackImageComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeedbackImageDetailComponent,
    resolve: {
      feedbackImage: FeedbackImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeedbackImageUpdateComponent,
    resolve: {
      feedbackImage: FeedbackImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeedbackImageUpdateComponent,
    resolve: {
      feedbackImage: FeedbackImageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(feedbackImageRoute)],
  exports: [RouterModule],
})
export class FeedbackImageRoutingModule {}
