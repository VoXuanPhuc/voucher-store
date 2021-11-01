import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FeedbackImageComponent } from './list/feedback-image.component';
import { FeedbackImageDetailComponent } from './detail/feedback-image-detail.component';
import { FeedbackImageUpdateComponent } from './update/feedback-image-update.component';
import { FeedbackImageDeleteDialogComponent } from './delete/feedback-image-delete-dialog.component';
import { FeedbackImageRoutingModule } from './route/feedback-image-routing.module';

@NgModule({
  imports: [SharedModule, FeedbackImageRoutingModule],
  declarations: [FeedbackImageComponent, FeedbackImageDetailComponent, FeedbackImageUpdateComponent, FeedbackImageDeleteDialogComponent],
  entryComponents: [FeedbackImageDeleteDialogComponent],
})
export class FeedbackImageModule {}
