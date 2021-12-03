import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FeedbackComponent } from './list/feedback.component';
import { FeedbackDetailComponent } from './detail/feedback-detail.component';
import { FeedbackUpdateComponent } from './update/feedback-update.component';
import { FeedbackDeleteDialogComponent } from './delete/feedback-delete-dialog.component';
import { FeedbackRoutingModule } from './route/feedback-routing.module';
import { VoucherRatingComponent } from './voucher-rating/voucher-rating.component';
import { FeedbackVoucherComponent } from './feedback-voucher/feedback-voucher.component';

@NgModule({
  imports: [SharedModule, FeedbackRoutingModule],
  declarations: [
    FeedbackComponent,
    FeedbackDetailComponent,
    FeedbackUpdateComponent,
    FeedbackDeleteDialogComponent,
    VoucherRatingComponent,
    FeedbackVoucherComponent,
  ],
  entryComponents: [FeedbackDeleteDialogComponent],
  exports: [VoucherRatingComponent, FeedbackVoucherComponent],
})
export class FeedbackModule {}
