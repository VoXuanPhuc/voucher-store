import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStoreUser, StoreUser } from '../store-user.model';
import { StoreUserService } from '../service/store-user.service';
import { IRelationshipType } from 'app/entities/relationship-type/relationship-type.model';
import { RelationshipTypeService } from 'app/entities/relationship-type/service/relationship-type.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IStore } from 'app/entities/store/store.model';
import { StoreService } from 'app/entities/store/service/store.service';

@Component({
  selector: 'jhi-store-user-update',
  templateUrl: './store-user-update.component.html',
})
export class StoreUserUpdateComponent implements OnInit {
  isSaving = false;

  relationshipTypesSharedCollection: IRelationshipType[] = [];
  myUsersSharedCollection: IMyUser[] = [];
  storesSharedCollection: IStore[] = [];

  editForm = this.fb.group({
    id: [],
    type: [],
    user: [],
    store: [],
  });

  constructor(
    protected storeUserService: StoreUserService,
    protected relationshipTypeService: RelationshipTypeService,
    protected myUserService: MyUserService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storeUser }) => {
      this.updateForm(storeUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const storeUser = this.createFromForm();
    if (storeUser.id !== undefined) {
      this.subscribeToSaveResponse(this.storeUserService.update(storeUser));
    } else {
      this.subscribeToSaveResponse(this.storeUserService.create(storeUser));
    }
  }

  trackRelationshipTypeById(index: number, item: IRelationshipType): number {
    return item.id!;
  }

  trackMyUserById(index: number, item: IMyUser): number {
    return item.id!;
  }

  trackStoreById(index: number, item: IStore): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStoreUser>>): void {
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

  protected updateForm(storeUser: IStoreUser): void {
    this.editForm.patchValue({
      id: storeUser.id,
      type: storeUser.type,
      user: storeUser.user,
      store: storeUser.store,
    });

    this.relationshipTypesSharedCollection = this.relationshipTypeService.addRelationshipTypeToCollectionIfMissing(
      this.relationshipTypesSharedCollection,
      storeUser.type
    );
    this.myUsersSharedCollection = this.myUserService.addMyUserToCollectionIfMissing(this.myUsersSharedCollection, storeUser.user);
    this.storesSharedCollection = this.storeService.addStoreToCollectionIfMissing(this.storesSharedCollection, storeUser.store);
  }

  protected loadRelationshipsOptions(): void {
    this.relationshipTypeService
      .query()
      .pipe(map((res: HttpResponse<IRelationshipType[]>) => res.body ?? []))
      .pipe(
        map((relationshipTypes: IRelationshipType[]) =>
          this.relationshipTypeService.addRelationshipTypeToCollectionIfMissing(relationshipTypes, this.editForm.get('type')!.value)
        )
      )
      .subscribe((relationshipTypes: IRelationshipType[]) => (this.relationshipTypesSharedCollection = relationshipTypes));

    this.myUserService
      .query()
      .pipe(map((res: HttpResponse<IMyUser[]>) => res.body ?? []))
      .pipe(map((myUsers: IMyUser[]) => this.myUserService.addMyUserToCollectionIfMissing(myUsers, this.editForm.get('user')!.value)))
      .subscribe((myUsers: IMyUser[]) => (this.myUsersSharedCollection = myUsers));

    this.storeService
      .query()
      .pipe(map((res: HttpResponse<IStore[]>) => res.body ?? []))
      .pipe(map((stores: IStore[]) => this.storeService.addStoreToCollectionIfMissing(stores, this.editForm.get('store')!.value)))
      .subscribe((stores: IStore[]) => (this.storesSharedCollection = stores));
  }

  protected createFromForm(): IStoreUser {
    return {
      ...new StoreUser(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      user: this.editForm.get(['user'])!.value,
      store: this.editForm.get(['store'])!.value,
    };
  }
}
