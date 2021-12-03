import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './list/feedback.component';
import { FeedbackDetailComponent } from './detail/feedback-detail.component';
import { FeedbackUpdateComponent } from './update/feedback-update.component';
import { FeedbackDeleteDialogComponent } from './delete/feedback-delete-dialog.component';
import { FeedbackRoutingModule } from './route/feedback-routing.module';
import { VoucherRatingComponent } from './voucher-rating/voucher-rating.component';

@NgModule({
  imports: [SharedModule, FeedbackRoutingModule],
  declarations: [
    FeedbackComponent,
    FeedbackDetailComponent,
    FeedbackUpdateComponent,
    FeedbackDeleteDialogComponent,
    VoucherRatingComponent,
  ],
  entryComponents: [FeedbackDeleteDialogComponent],
  exports: [VoucherRatingComponent],
})
export class FeedbackModule {}
