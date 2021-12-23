import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStore, Store } from '../store.model';
import { StoreService } from '../service/store.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IBenifitPackage } from 'app/entities/benifit-package/benifit-package.model';
import { BenifitPackageService } from 'app/entities/benifit-package/service/benifit-package.service';

@Component({
    selector: 'jhi-store-update',
    templateUrl: './store-update.component.html',
})
export class StoreUpdateComponent implements OnInit {
    isSaving = false;

    addressesCollection: IAddress[] = [];
    benifitPackagesSharedCollection: IBenifitPackage[] = [];

    editForm = this.fb.group({
        id: [],
        name: [null, [Validators.required]],
        description: [],
        email: [null, [Validators.required]],
        phone: [null, [Validators.required]],
        avartar: [],
        background: [],
        address: [],
        benifit: [],
    });

    constructor(
        protected storeService: StoreService,
        protected addressService: AddressService,
        protected benifitPackageService: BenifitPackageService,
        protected activatedRoute: ActivatedRoute,
        protected fb: FormBuilder
    ) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ store }) => {
            this.updateForm(store);

            this.loadRelationshipsOptions();
        });
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        const store = this.createFromForm();
        if (store.id !== undefined) {
            this.subscribeToSaveResponse(this.storeService.update(store));
        } else {
            this.subscribeToSaveResponse(this.storeService.create(store));
        }
    }

    trackAddressById(index: number, item: IAddress): number {
        return item.id!;
    }

    trackBenifitPackageById(index: number, item: IBenifitPackage): number {
        return item.id!;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>): void {
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

    protected updateForm(store: IStore): void {
        this.editForm.patchValue({
            id: store.id,
            name: store.name,
            description: store.description,
            email: store.email,
            phone: store.phone,
            avartar: store.avartar,
            background: store.background,
            address: store.address,
            benifit: store.benifit,
        });

        this.addressesCollection = this.addressService.addAddressToCollectionIfMissing(this.addressesCollection, store.address);
        this.benifitPackagesSharedCollection = this.benifitPackageService.addBenifitPackageToCollectionIfMissing(
            this.benifitPackagesSharedCollection,
            store.benifit
        );
    }

    protected loadRelationshipsOptions(): void {
        this.addressService
            .query({ filter: 'store-is-null' })
            .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
            .pipe(
                map((addresses: IAddress[]) =>
                    this.addressService.addAddressToCollectionIfMissing(addresses, this.editForm.get('address')!.value)
                )
            )
            .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));

        this.benifitPackageService
            .query()
            .pipe(map((res: HttpResponse<IBenifitPackage[]>) => res.body ?? []))
            .pipe(
                map((benifitPackages: IBenifitPackage[]) =>
                    this.benifitPackageService.addBenifitPackageToCollectionIfMissing(benifitPackages, this.editForm.get('benifit')!.value)
                )
            )
            .subscribe((benifitPackages: IBenifitPackage[]) => (this.benifitPackagesSharedCollection = benifitPackages));
    }

    protected createFromForm(): IStore {
        return {
            ...new Store(),
            id: this.editForm.get(['id'])!.value,
            name: this.editForm.get(['name'])!.value,
            // description: this.editForm.get(['description'])!.value,
            email: this.editForm.get(['email'])!.value,
            phone: this.editForm.get(['phone'])!.value,
            avartar: this.editForm.get(['avartar'])!.value,
            background: this.editForm.get(['background'])!.value,
            address: this.editForm.get(['address'])!.value,
            benifit: this.editForm.get(['benifit'])!.value,
        };
    }
}
