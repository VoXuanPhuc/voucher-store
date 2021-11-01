import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMyUser, MyUser } from '../my-user.model';
import { MyUserService } from '../service/my-user.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IRole } from 'app/entities/role/role.model';
import { RoleService } from 'app/entities/role/service/role.service';

@Component({
  selector: 'jhi-my-user-update',
  templateUrl: './my-user-update.component.html',
})
export class MyUserUpdateComponent implements OnInit {
  isSaving = false;

  addressesCollection: IAddress[] = [];
  rolesSharedCollection: IRole[] = [];

  editForm = this.fb.group({
    id: [],
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    email: [null, [Validators.required]],
    address: [],
    role: [],
  });

  constructor(
    protected myUserService: MyUserService,
    protected addressService: AddressService,
    protected roleService: RoleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myUser }) => {
      this.updateForm(myUser);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const myUser = this.createFromForm();
    if (myUser.id !== undefined) {
      this.subscribeToSaveResponse(this.myUserService.update(myUser));
    } else {
      this.subscribeToSaveResponse(this.myUserService.create(myUser));
    }
  }

  trackAddressById(index: number, item: IAddress): number {
    return item.id!;
  }

  trackRoleById(index: number, item: IRole): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMyUser>>): void {
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

  protected updateForm(myUser: IMyUser): void {
    this.editForm.patchValue({
      id: myUser.id,
      username: myUser.username,
      password: myUser.password,
      firstName: myUser.firstName,
      lastName: myUser.lastName,
      gender: myUser.gender,
      phone: myUser.phone,
      email: myUser.email,
      address: myUser.address,
      role: myUser.role,
    });

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, myUser.address);
    this.rolesSharedCollection = this.roleService.addRoleToCollectionIfMissing(this.rolesSharedCollection, myUser.role);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'myuser-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value))
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));

    this.roleService
      .query()
      .pipe(map((res: HttpResponse<IRole[]>) => res.body ?? []))
      .pipe(map((roles: IRole[]) => this.roleService.addRoleToCollectionIfMissing(roles, this.editForm.get('role')!.value)))
      .subscribe((roles: IRole[]) => (this.rolesSharedCollection = roles));
  }

  protected createFromForm(): IMyUser {
    return {
      ...new MyUser(),
      id: this.editForm.get(['id'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      address: this.editForm.get(['address'])!.value,
      role: this.editForm.get(['role'])!.value,
    };
  }
}
