import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFeedback, Feedback } from '../feedback.model';
import { FeedbackService } from '../service/feedback.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

@Component({
  selector: 'jhi-feedback-update',
  templateUrl: './feedback-update.component.html',
})
export class FeedbackUpdateComponent implements OnInit {
  isSaving = false;

  myUsersSharedCollection: IMyUser[] = [];
  vouchersSharedCollection: IVoucher[] = [];

  editForm = this.fb.group({
    id: [],
    rate: [null, [Validators.required]],
    detail: [],
    user: [],
    voucher: [],
  });

  constructor(
    protected feedbackService: FeedbackService,
    protected myUserService: MyUserService,
    protected voucherService: VoucherService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.updateForm(feedback);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedback = this.createFromForm();
    if (feedback.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackService.update(feedback));
    } else {
      this.subscribeToSaveResponse(this.feedbackService.create(feedback));
    }
  }

  trackMyUserById(index: number, item: IMyUser): number {
    return item.id!;
  }

  trackVoucherById(index: number, item: IVoucher): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedback>>): void {
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

  protected updateForm(feedback: IFeedback): void {
    this.editForm.patchValue({
      id: feedback.id,
      rate: feedback.rate,
      detail: feedback.detail,
      user: feedback.user,
      voucher: feedback.voucher,
    });

    this.myUsersSharedCollection = this.myUserService.addMyUserToCollectionIfMissing(this.myUsersSharedCollection, feedback.user);
    this.vouchersSharedCollection = this.voucherService.addVoucherToCollectionIfMissing(this.vouchersSharedCollection, feedback.voucher);
  }

  protected loadRelationshipsOptions(): void {
    this.myUserService
      .query()
      .pipe(map((res: HttpResponse<IMyUser[]>) => res.body ?? []))
      .pipe(map((myUsers: IMyUser[]) => this.myUserService.addMyUserToCollectionIfMissing(myUsers, this.editForm.get('user')!.value)))
      .subscribe((myUsers: IMyUser[]) => (this.myUsersSharedCollection = myUsers));

    this.voucherService
      .query()
      .pipe(map((res: HttpResponse<IVoucher[]>) => res.body ?? []))
      .pipe(
        map((vouchers: IVoucher[]) => this.voucherService.addVoucherToCollectionIfMissing(vouchers, this.editForm.get('voucher')!.value))
      )
      .subscribe((vouchers: IVoucher[]) => (this.vouchersSharedCollection = vouchers));
  }

  protected createFromForm(): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id'])!.value,
      rate: this.editForm.get(['rate'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      user: this.editForm.get(['user'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
    };
  }
}
