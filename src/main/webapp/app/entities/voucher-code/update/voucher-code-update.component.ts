import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVoucherCode, VoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';
import { IVoucherStatus } from 'app/entities/voucher-status/voucher-status.model';
import { VoucherStatusService } from 'app/entities/voucher-status/service/voucher-status.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IMyOrder } from 'app/entities/my-order/my-order.model';
import { MyOrderService } from 'app/entities/my-order/service/my-order.service';

@Component({
  selector: 'jhi-voucher-code-update',
  templateUrl: './voucher-code-update.component.html',
})
export class VoucherCodeUpdateComponent implements OnInit {
  isSaving = false;

  voucherStatusesSharedCollection: IVoucherStatus[] = [];
  vouchersSharedCollection: IVoucher[] = [];
  myOrdersSharedCollection: IMyOrder[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    status: [],
    voucher: [],
    order: [],
  });

  constructor(
    protected voucherCodeService: VoucherCodeService,
    protected voucherStatusService: VoucherStatusService,
    protected voucherService: VoucherService,
    protected myOrderService: MyOrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherCode }) => {
      this.updateForm(voucherCode);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucherCode = this.createFromForm();
    if (voucherCode.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherCodeService.update(voucherCode));
    } else {
      this.subscribeToSaveResponse(this.voucherCodeService.create(voucherCode));
    }
  }

  trackVoucherStatusById(index: number, item: IVoucherStatus): number {
    return item.id!;
  }

  trackVoucherById(index: number, item: IVoucher): number {
    return item.id!;
  }

  trackMyOrderById(index: number, item: IMyOrder): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherCode>>): void {
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

  protected updateForm(voucherCode: IVoucherCode): void {
    this.editForm.patchValue({
      id: voucherCode.id,
      code: voucherCode.code,
      status: voucherCode.status,
      voucher: voucherCode.voucher,
      order: voucherCode.order,
    });

    this.voucherStatusesSharedCollection = this.voucherStatusService.addVoucherStatusToCollectionIfMissing(
      this.voucherStatusesSharedCollection,
      voucherCode.status
    );
    this.vouchersSharedCollection = this.voucherService.addVoucherToCollectionIfMissing(this.vouchersSharedCollection, voucherCode.voucher);
    this.myOrdersSharedCollection = this.myOrderService.addMyOrderToCollectionIfMissing(this.myOrdersSharedCollection, voucherCode.order);
  }

  protected loadRelationshipsOptions(): void {
    this.voucherStatusService
      .query()
      .pipe(map((res: HttpResponse<IVoucherStatus[]>) => res.body ?? []))
      .pipe(
        map((voucherStatuses: IVoucherStatus[]) =>
          this.voucherStatusService.addVoucherStatusToCollectionIfMissing(voucherStatuses, this.editForm.get('status')!.value)
        )
      )
      .subscribe((voucherStatuses: IVoucherStatus[]) => (this.voucherStatusesSharedCollection = voucherStatuses));

    this.voucherService
      .query()
      .pipe(map((res: HttpResponse<IVoucher[]>) => res.body ?? []))
      .pipe(
        map((vouchers: IVoucher[]) => this.voucherService.addVoucherToCollectionIfMissing(vouchers, this.editForm.get('voucher')!.value))
      )
      .subscribe((vouchers: IVoucher[]) => (this.vouchersSharedCollection = vouchers));

    this.myOrderService
      .query()
      .pipe(map((res: HttpResponse<IMyOrder[]>) => res.body ?? []))
      .pipe(map((myOrders: IMyOrder[]) => this.myOrderService.addMyOrderToCollectionIfMissing(myOrders, this.editForm.get('order')!.value)))
      .subscribe((myOrders: IMyOrder[]) => (this.myOrdersSharedCollection = myOrders));
  }

  protected createFromForm(): IVoucherCode {
    return {
      ...new VoucherCode(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      status: this.editForm.get(['status'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
