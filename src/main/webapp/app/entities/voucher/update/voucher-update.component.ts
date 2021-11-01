import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVoucher, Voucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IServiceType } from 'app/entities/service-type/service-type.model';
import { ServiceTypeService } from 'app/entities/service-type/service/service-type.service';
import { IVoucherStatus } from 'app/entities/voucher-status/voucher-status.model';
import { VoucherStatusService } from 'app/entities/voucher-status/service/voucher-status.service';

@Component({
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html',
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  eventsSharedCollection: IEvent[] = [];
  serviceTypesSharedCollection: IServiceType[] = [];
  voucherStatusesSharedCollection: IVoucherStatus[] = [];

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    startTime: [null, [Validators.required]],
    expriedTime: [null, [Validators.required]],
    products: [],
    event: [],
    type: [],
    status: [],
  });

  constructor(
    protected voucherService: VoucherService,
    protected productService: ProductService,
    protected eventService: EventService,
    protected serviceTypeService: ServiceTypeService,
    protected voucherStatusService: VoucherStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      if (voucher.id === undefined) {
        const today = dayjs().startOf('day');
        voucher.startTime = today;
        voucher.expriedTime = today;
      }

      this.updateForm(voucher);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucher = this.createFromForm();
    if (voucher.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherService.update(voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(voucher));
    }
  }

  trackProductById(index: number, item: IProduct): number {
    return item.id!;
  }

  trackEventById(index: number, item: IEvent): number {
    return item.id!;
  }

  trackServiceTypeById(index: number, item: IServiceType): number {
    return item.id!;
  }

  trackVoucherStatusById(index: number, item: IVoucherStatus): number {
    return item.id!;
  }

  getSelectedProduct(option: IProduct, selectedVals?: IProduct[]): IProduct {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
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

  protected updateForm(voucher: IVoucher): void {
    this.editForm.patchValue({
      id: voucher.id,
      price: voucher.price,
      quantity: voucher.quantity,
      startTime: voucher.startTime ? voucher.startTime.format(DATE_TIME_FORMAT) : null,
      expriedTime: voucher.expriedTime ? voucher.expriedTime.format(DATE_TIME_FORMAT) : null,
      products: voucher.products,
      event: voucher.event,
      type: voucher.type,
      status: voucher.status,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      ...(voucher.products ?? [])
    );
    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing(this.eventsSharedCollection, voucher.event);
    this.serviceTypesSharedCollection = this.serviceTypeService.addServiceTypeToCollectionIfMissing(
      this.serviceTypesSharedCollection,
      voucher.type
    );
    this.voucherStatusesSharedCollection = this.voucherStatusService.addVoucherStatusToCollectionIfMissing(
      this.voucherStatusesSharedCollection,
      voucher.status
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing(products, ...(this.editForm.get('products')!.value ?? []))
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.eventService
      .query()
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing(events, this.editForm.get('event')!.value)))
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));

    this.serviceTypeService
      .query()
      .pipe(map((res: HttpResponse<IServiceType[]>) => res.body ?? []))
      .pipe(
        map((serviceTypes: IServiceType[]) =>
          this.serviceTypeService.addServiceTypeToCollectionIfMissing(serviceTypes, this.editForm.get('type')!.value)
        )
      )
      .subscribe((serviceTypes: IServiceType[]) => (this.serviceTypesSharedCollection = serviceTypes));

    this.voucherStatusService
      .query()
      .pipe(map((res: HttpResponse<IVoucherStatus[]>) => res.body ?? []))
      .pipe(
        map((voucherStatuses: IVoucherStatus[]) =>
          this.voucherStatusService.addVoucherStatusToCollectionIfMissing(voucherStatuses, this.editForm.get('status')!.value)
        )
      )
      .subscribe((voucherStatuses: IVoucherStatus[]) => (this.voucherStatusesSharedCollection = voucherStatuses));
  }

  protected createFromForm(): IVoucher {
    return {
      ...new Voucher(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? dayjs(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      expriedTime: this.editForm.get(['expriedTime'])!.value
        ? dayjs(this.editForm.get(['expriedTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      products: this.editForm.get(['products'])!.value,
      event: this.editForm.get(['event'])!.value,
      type: this.editForm.get(['type'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
