import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedbackImage } from '../feedback-image.model';
import { FeedbackImageService } from '../service/feedback-image.service';
import { FeedbackImageDeleteDialogComponent } from '../delete/feedback-image-delete-dialog.component';

@Component({
  selector: 'jhi-feedback-image',
  templateUrl: './feedback-image.component.html',
})
export class FeedbackImageComponent implements OnInit {
  feedbackImages?: IFeedbackImage[];
  isLoading = false;

  constructor(protected feedbackImageService: FeedbackImageService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.feedbackImageService.query().subscribe(
      (res: HttpResponse<IFeedbackImage[]>) => {
        this.isLoading = false;
        this.feedbackImages = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFeedbackImage): number {
    return item.id!;
  }

  delete(feedbackImage: IFeedbackImage): void {
    const modalRef = this.modalService.open(FeedbackImageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feedbackImage = feedbackImage;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
