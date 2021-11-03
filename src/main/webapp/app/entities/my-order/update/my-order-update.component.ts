import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMyOrder, MyOrder } from '../my-order.model';
import { MyOrderService } from '../service/my-order.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IOrderStatus } from 'app/entities/order-status/order-status.model';
import { OrderStatusService } from 'app/entities/order-status/service/order-status.service';

@Component({
  selector: 'jhi-my-order-update',
  templateUrl: './my-order-update.component.html',
})
export class MyOrderUpdateComponent implements OnInit {
  isSaving = false;

  myUsersSharedCollection: IMyUser[] = [];
  orderStatusesSharedCollection: IOrderStatus[] = [];

  editForm = this.fb.group({
    id: [],
    totalCost: [null, [Validators.required]],
    paymentTime: [null, [Validators.required]],
    user: [],
    status: [],
  });

  constructor(
    protected myOrderService: MyOrderService,
    protected myUserService: MyUserService,
    protected orderStatusService: OrderStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myOrder }) => {
      if (myOrder.id === undefined) {
        const today = dayjs().startOf('day');
        myOrder.paymentTime = today;
      }

      this.updateForm(myOrder);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const myOrder = this.createFromForm();
    if (myOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.myOrderService.update(myOrder));
    } else {
      this.subscribeToSaveResponse(this.myOrderService.create(myOrder));
    }
  }

  trackMyUserById(index: number, item: IMyUser): number {
    return item.id!;
  }

  trackOrderStatusById(index: number, item: IOrderStatus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMyOrder>>): void {
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

  protected updateForm(myOrder: IMyOrder): void {
    this.editForm.patchValue({
      id: myOrder.id,
      totalCost: myOrder.totalCost,
      paymentTime: myOrder.paymentTime ? myOrder.paymentTime.format(DATE_TIME_FORMAT) : null,
      user: myOrder.user,
      status: myOrder.status,
    });

    this.myUsersSharedCollection = this.myUserService.addMyUserToCollectionIfMissing(this.myUsersSharedCollection, myOrder.user);
    this.orderStatusesSharedCollection = this.orderStatusService.addOrderStatusToCollectionIfMissing(
      this.orderStatusesSharedCollection,
      myOrder.status
    );
  }

  protected loadRelationshipsOptions(): void {
    this.myUserService
      .query()
      .pipe(map((res: HttpResponse<IMyUser[]>) => res.body ?? []))
      .pipe(map((myUsers: IMyUser[]) => this.myUserService.addMyUserToCollectionIfMissing(myUsers, this.editForm.get('user')!.value)))
      .subscribe((myUsers: IMyUser[]) => (this.myUsersSharedCollection = myUsers));

    this.orderStatusService
      .query()
      .pipe(map((res: HttpResponse<IOrderStatus[]>) => res.body ?? []))
      .pipe(
        map((orderStatuses: IOrderStatus[]) =>
          this.orderStatusService.addOrderStatusToCollectionIfMissing(orderStatuses, this.editForm.get('status')!.value)
        )
      )
      .subscribe((orderStatuses: IOrderStatus[]) => (this.orderStatusesSharedCollection = orderStatuses));
  }

  protected createFromForm(): IMyOrder {
    return {
      ...new MyOrder(),
      id: this.editForm.get(['id'])!.value,
      totalCost: this.editForm.get(['totalCost'])!.value,
      paymentTime: this.editForm.get(['paymentTime'])!.value
        ? dayjs(this.editForm.get(['paymentTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      user: this.editForm.get(['user'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }
}
