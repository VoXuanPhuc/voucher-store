import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBenifitPackage, BenifitPackage } from '../benifit-package.model';
import { BenifitPackageService } from '../service/benifit-package.service';

@Component({
  selector: 'jhi-benifit-package-update',
  templateUrl: './benifit-package-update.component.html',
})
export class BenifitPackageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    cost: [null, [Validators.required]],
    time: [null, [Validators.required]],
  });

  constructor(
    protected benifitPackageService: BenifitPackageService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ benifitPackage }) => {
      this.updateForm(benifitPackage);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const benifitPackage = this.createFromForm();
    if (benifitPackage.id !== undefined) {
      this.subscribeToSaveResponse(this.benifitPackageService.update(benifitPackage));
    } else {
      this.subscribeToSaveResponse(this.benifitPackageService.create(benifitPackage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBenifitPackage>>): void {
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

  protected updateForm(benifitPackage: IBenifitPackage): void {
    this.editForm.patchValue({
      id: benifitPackage.id,
      name: benifitPackage.name,
      description: benifitPackage.description,
      cost: benifitPackage.cost,
      time: benifitPackage.time,
    });
  }

  protected createFromForm(): IBenifitPackage {
    return {
      ...new BenifitPackage(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      cost: this.editForm.get(['cost'])!.value,
      time: this.editForm.get(['time'])!.value,
    };
  }
}
