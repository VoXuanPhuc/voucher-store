import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVoucherCode, VoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';

@Component({
  selector: 'jhi-voucher-code-update',
  templateUrl: './voucher-code-update.component.html',
})
export class VoucherCodeUpdateComponent implements OnInit {
  isSaving = false;

  vouchersSharedCollection: IVoucher[] = [];
  ordersSharedCollection: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    voucher: [],
    order: [],
  });

  constructor(
    protected voucherCodeService: VoucherCodeService,
    protected voucherService: VoucherService,
    protected orderService: OrderService,
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

  trackVoucherById(index: number, item: IVoucher): number {
    return item.id!;
  }

  trackOrderById(index: number, item: IOrder): number {
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
      voucher: voucherCode.voucher,
      order: voucherCode.order,
    });

    this.vouchersSharedCollection = this.voucherService.addVoucherToCollectionIfMissing(this.vouchersSharedCollection, voucherCode.voucher);
    this.ordersSharedCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersSharedCollection, voucherCode.order);
  }

  protected loadRelationshipsOptions(): void {
    this.voucherService
      .query()
      .pipe(map((res: HttpResponse<IVoucher[]>) => res.body ?? []))
      .pipe(
        map((vouchers: IVoucher[]) => this.voucherService.addVoucherToCollectionIfMissing(vouchers, this.editForm.get('voucher')!.value))
      )
      .subscribe((vouchers: IVoucher[]) => (this.vouchersSharedCollection = vouchers));

    this.orderService
      .query()
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersSharedCollection = orders));
  }

  protected createFromForm(): IVoucherCode {
    return {
      ...new VoucherCode(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      voucher: this.editForm.get(['voucher'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
