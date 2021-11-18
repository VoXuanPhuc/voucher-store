import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRole, Role } from '../role.model';
import { RoleService } from '../service/role.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';

@Component({
  selector: 'jhi-role-update',
  templateUrl: './role-update.component.html',
})
export class RoleUpdateComponent implements OnInit {
  isSaving = false;

  myUsersSharedCollection: IMyUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [null, [Validators.required]],
    users: [],
  });

  constructor(
    protected roleService: RoleService,
    protected myUserService: MyUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ role }) => {
      this.updateForm(role);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const role = this.createFromForm();
    if (role.id !== undefined) {
      this.subscribeToSaveResponse(this.roleService.update(role));
    } else {
      this.subscribeToSaveResponse(this.roleService.create(role));
    }
  }

  trackMyUserById(index: number, item: IMyUser): number {
    return item.id!;
  }

  getSelectedMyUser(option: IMyUser, selectedVals?: IMyUser[]): IMyUser {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>): void {
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

  protected updateForm(role: IRole): void {
    this.editForm.patchValue({
      id: role.id,
      name: role.name,
      code: role.code,
      users: role.users,
    });

    this.myUsersSharedCollection = this.myUserService.addMyUserToCollectionIfMissing(this.myUsersSharedCollection, ...(role.users ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.myUserService
      .query()
      .pipe(map((res: HttpResponse<IMyUser[]>) => res.body ?? []))
      .pipe(
        map((myUsers: IMyUser[]) =>
          this.myUserService.addMyUserToCollectionIfMissing(myUsers, ...(this.editForm.get('users')!.value ?? []))
        )
      )
      .subscribe((myUsers: IMyUser[]) => (this.myUsersSharedCollection = myUsers));
  }

  protected createFromForm(): IRole {
    return {
      ...new Role(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
      users: this.editForm.get(['users'])!.value,
    };
  }
}
