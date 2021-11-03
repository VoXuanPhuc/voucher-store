import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrderStatus, OrderStatus } from '../order-status.model';
import { OrderStatusService } from '../service/order-status.service';

@Component({
  selector: 'jhi-order-status-update',
  templateUrl: './order-status-update.component.html',
})
export class OrderStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected orderStatusService: OrderStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderStatus }) => {
      this.updateForm(orderStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderStatus = this.createFromForm();
    if (orderStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.orderStatusService.update(orderStatus));
    } else {
      this.subscribeToSaveResponse(this.orderStatusService.create(orderStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderStatus>>): void {
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

  protected updateForm(orderStatus: IOrderStatus): void {
    this.editForm.patchValue({
      id: orderStatus.id,
      name: orderStatus.name,
    });
  }

  protected createFromForm(): IOrderStatus {
    return {
      ...new OrderStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
