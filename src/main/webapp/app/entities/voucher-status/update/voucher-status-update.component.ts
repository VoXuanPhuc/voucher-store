import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVoucherStatus, VoucherStatus } from '../voucher-status.model';
import { VoucherStatusService } from '../service/voucher-status.service';

@Component({
  selector: 'jhi-voucher-status-update',
  templateUrl: './voucher-status-update.component.html',
})
export class VoucherStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected voucherStatusService: VoucherStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherStatus }) => {
      this.updateForm(voucherStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucherStatus = this.createFromForm();
    if (voucherStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherStatusService.update(voucherStatus));
    } else {
      this.subscribeToSaveResponse(this.voucherStatusService.create(voucherStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherStatus>>): void {
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

  protected updateForm(voucherStatus: IVoucherStatus): void {
    this.editForm.patchValue({
      id: voucherStatus.id,
      name: voucherStatus.name,
    });
  }

  protected createFromForm(): IVoucherStatus {
    return {
      ...new VoucherStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
