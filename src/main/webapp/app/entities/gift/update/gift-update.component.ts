import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IGift, Gift } from '../gift.model';
import { GiftService } from '../service/gift.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';

@Component({
  selector: 'jhi-gift-update',
  templateUrl: './gift-update.component.html',
})
export class GiftUpdateComponent implements OnInit {
  isSaving = false;

  myUsersSharedCollection: IMyUser[] = [];
  voucherCodesSharedCollection: IVoucherCode[] = [];

  editForm = this.fb.group({
    id: [],
    message: [],
    giver: [],
    voucher: [],
  });

  constructor(
    protected giftService: GiftService,
    protected myUserService: MyUserService,
    protected voucherCodeService: VoucherCodeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gift }) => {
      this.updateForm(gift);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gift = this.createFromForm();
    if (gift.id !== undefined) {
      this.subscribeToSaveResponse(this.giftService.update(gift));
    } else {
      this.subscribeToSaveResponse(this.giftService.create(gift));
    }
  }

  trackMyUserById(index: number, item: IMyUser): number {
    return item.id!;
  }

  trackVoucherCodeById(index: number, item: IVoucherCode): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGift>>): void {
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

  protected updateForm(gift: IGift): void {
    this.editForm.patchValue({
      id: gift.id,
      message: gift.message,
      giver: gift.giver,
      voucher: gift.voucher,
    });

    this.myUsersSharedCollection = this.myUserService.addMyUserToCollectionIfMissing(this.myUsersSharedCollection, gift.giver);
    this.voucherCodesSharedCollection = this.voucherCodeService.addVoucherCodeToCollectionIfMissing(
      this.voucherCodesSharedCollection,
      gift.voucher
    );
  }

  protected loadRelationshipsOptions(): void {
    this.myUserService
      .query()
      .pipe(map((res: HttpResponse<IMyUser[]>) => res.body ?? []))
      .pipe(map((myUsers: IMyUser[]) => this.myUserService.addMyUserToCollectionIfMissing(myUsers, this.editForm.get('giver')!.value)))
      .subscribe((myUsers: IMyUser[]) => (this.myUsersSharedCollection = myUsers));

    this.voucherCodeService
      .query()
      .pipe(map((res: HttpResponse<IVoucherCode[]>) => res.body ?? []))
      .pipe(
        map((voucherCodes: IVoucherCode[]) =>
          this.voucherCodeService.addVoucherCodeToCollectionIfMissing(voucherCodes, this.editForm.get('voucher')!.value)
        )
      )
      .subscribe((voucherCodes: IVoucherCode[]) => (this.voucherCodesSharedCollection = voucherCodes));
  }

  protected createFromForm(): IGift {
    return {
      ...new Gift(),
      id: this.editForm.get(['id'])!.value,
      message: this.editForm.get(['message'])!.value,
      giver: this.editForm.get(['giver'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
    };
  }
}
