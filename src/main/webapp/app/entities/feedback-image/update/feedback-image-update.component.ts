import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFeedbackImage, FeedbackImage } from '../feedback-image.model';
import { FeedbackImageService } from '../service/feedback-image.service';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

@Component({
  selector: 'jhi-feedback-image-update',
  templateUrl: './feedback-image-update.component.html',
})
export class FeedbackImageUpdateComponent implements OnInit {
  isSaving = false;

  feedbacksSharedCollection: IFeedback[] = [];

  editForm = this.fb.group({
    id: [],
    content: [null, [Validators.required]],
    feedback: [],
  });

  constructor(
    protected feedbackImageService: FeedbackImageService,
    protected feedbackService: FeedbackService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedbackImage }) => {
      this.updateForm(feedbackImage);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedbackImage = this.createFromForm();
    if (feedbackImage.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackImageService.update(feedbackImage));
    } else {
      this.subscribeToSaveResponse(this.feedbackImageService.create(feedbackImage));
    }
  }

  trackFeedbackById(index: number, item: IFeedback): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedbackImage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(feedbackImage: IFeedbackImage): void {
    this.editForm.patchValue({
      id: feedbackImage.id,
      content: feedbackImage.content,
      feedback: feedbackImage.feedback,
    });

    this.feedbacksSharedCollection = this.feedbackService.addFeedbackToCollectionIfMissing(
      this.feedbacksSharedCollection,
      feedbackImage.feedback
    );
  }

  protected loadRelationshipsOptions(): void {
    this.feedbackService
      .query()
      .pipe(map((res: HttpResponse<IFeedback[]>) => res.body ?? []))
      .pipe(
        map((feedbacks: IFeedback[]) =>
          this.feedbackService.addFeedbackToCollectionIfMissing(feedbacks, this.editForm.get('feedback')!.value)
        )
      )
      .subscribe((feedbacks: IFeedback[]) => (this.feedbacksSharedCollection = feedbacks));
  }

  protected createFromForm(): IFeedbackImage {
    return {
      ...new FeedbackImage(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      feedback: this.editForm.get(['feedback'])!.value,
    };
  }
}
