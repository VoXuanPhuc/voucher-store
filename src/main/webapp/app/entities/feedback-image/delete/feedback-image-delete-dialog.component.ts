import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedbackImage } from '../feedback-image.model';
import { FeedbackImageService } from '../service/feedback-image.service';

@Component({
  templateUrl: './feedback-image-delete-dialog.component.html',
})
export class FeedbackImageDeleteDialogComponent {
  feedbackImage?: IFeedbackImage;

  constructor(protected feedbackImageService: FeedbackImageService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedbackImageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
