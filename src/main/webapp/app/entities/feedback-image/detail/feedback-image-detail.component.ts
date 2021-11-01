import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeedbackImage } from '../feedback-image.model';

@Component({
  selector: 'jhi-feedback-image-detail',
  templateUrl: './feedback-image-detail.component.html',
})
export class FeedbackImageDetailComponent implements OnInit {
  feedbackImage: IFeedbackImage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedbackImage }) => {
      this.feedbackImage = feedbackImage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
