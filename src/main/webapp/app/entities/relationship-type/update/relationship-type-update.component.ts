import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRelationshipType, RelationshipType } from '../relationship-type.model';
import { RelationshipTypeService } from '../service/relationship-type.service';

@Component({
  selector: 'jhi-relationship-type-update',
  templateUrl: './relationship-type-update.component.html',
})
export class RelationshipTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(
    protected relationshipTypeService: RelationshipTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relationshipType }) => {
      this.updateForm(relationshipType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relationshipType = this.createFromForm();
    if (relationshipType.id !== undefined) {
      this.subscribeToSaveResponse(this.relationshipTypeService.update(relationshipType));
    } else {
      this.subscribeToSaveResponse(this.relationshipTypeService.create(relationshipType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelationshipType>>): void {
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

  protected updateForm(relationshipType: IRelationshipType): void {
    this.editForm.patchValue({
      id: relationshipType.id,
      name: relationshipType.name,
    });
  }

  protected createFromForm(): IRelationshipType {
    return {
      ...new RelationshipType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
