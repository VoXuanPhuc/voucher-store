import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IServiceType, ServiceType } from '../service-type.model';
import { ServiceTypeService } from '../service/service-type.service';

@Component({
  selector: 'jhi-service-type-update',
  templateUrl: './service-type-update.component.html',
})
export class ServiceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    icon: [],
  });

  constructor(protected serviceTypeService: ServiceTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceType }) => {
      this.updateForm(serviceType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceType = this.createFromForm();
    if (serviceType.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceTypeService.update(serviceType));
    } else {
      this.subscribeToSaveResponse(this.serviceTypeService.create(serviceType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceType>>): void {
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

  protected updateForm(serviceType: IServiceType): void {
    this.editForm.patchValue({
      id: serviceType.id,
      name: serviceType.name,
      icon: serviceType.icon,
    });
  }

  protected createFromForm(): IServiceType {
    return {
      ...new ServiceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      icon: this.editForm.get(['icon'])!.value,
    };
  }
}
