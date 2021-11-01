import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVoucherImage, VoucherImage } from '../voucher-image.model';
import { VoucherImageService } from '../service/voucher-image.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

@Component({
  selector: 'jhi-voucher-image-update',
  templateUrl: './voucher-image-update.component.html',
})
export class VoucherImageUpdateComponent implements OnInit {
  isSaving = false;

  vouchersSharedCollection: IVoucher[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    voucher: [],
  });

  constructor(
    protected voucherImageService: VoucherImageService,
    protected voucherService: VoucherService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherImage }) => {
      this.updateForm(voucherImage);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucherImage = this.createFromForm();
    if (voucherImage.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherImageService.update(voucherImage));
    } else {
      this.subscribeToSaveResponse(this.voucherImageService.create(voucherImage));
    }
  }

  trackVoucherById(index: number, item: IVoucher): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherImage>>): void {
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

  protected updateForm(voucherImage: IVoucherImage): void {
    this.editForm.patchValue({
      id: voucherImage.id,
      name: voucherImage.name,
      voucher: voucherImage.voucher,
    });

    this.vouchersSharedCollection = this.voucherService.addVoucherToCollectionIfMissing(
      this.vouchersSharedCollection,
      voucherImage.voucher
    );
  }

  protected loadRelationshipsOptions(): void {
    this.voucherService
      .query()
      .pipe(map((res: HttpResponse<IVoucher[]>) => res.body ?? []))
      .pipe(
        map((vouchers: IVoucher[]) => this.voucherService.addVoucherToCollectionIfMissing(vouchers, this.editForm.get('voucher')!.value))
      )
      .subscribe((vouchers: IVoucher[]) => (this.vouchersSharedCollection = vouchers));
  }

  protected createFromForm(): IVoucherImage {
    return {
      ...new VoucherImage(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
    };
  }
}
