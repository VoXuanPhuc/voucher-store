import { IAddress } from './../../address/address.model';
import { ProvinceService } from './../../province/service/province.service';
import { DistrictService } from './../../district/service/district.service';
import { District, IDistrict } from './../../district/district.model';
import { VillageService } from './../../village/service/village.service';
import { Village, IVillage } from './../../village/village.model';
import { AddressService } from './../../address/service/address.service';
import { Store, IStore } from './../store.model';
import { StoreService } from './../service/store.service';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

@Component({
    selector: 'jhi-all-store',
    templateUrl: './all-store.component.html',
    styleUrls: ['./all-store.component.scss'],
})
export class AllStoreComponent implements OnInit, OnChanges {
    isLoading = false;
    @Input() stores?: Store[];

    constructor(
        private storeService: StoreService,
        private addressService: AddressService,
        private villageService: VillageService,
        private districtService: DistrictService,
        private provinceService: ProvinceService
    ) {}

    ngOnInit(): void {
        return;
    }

    ngOnChanges(changes: SimpleChanges): void {
        for (const property in changes) {
            if (property === 'stores') {
                this.loadAddress(this.stores ?? []);
            }
        }
    }

    loadAll(): void {
        this.storeService.query().subscribe(
            res => {
                this.isLoading = true;
                this.stores = res.body ?? [];

                this.loadAddress(this.stores);

                window.console.log('Stores: ', this.stores);
            },
            error => {
                window.console.log('Error: ', error);
            }
        );
    }

    loadAddress(stores: IStore[]): void {
        stores.forEach(store => {
            this.addressService.find(store.address?.id ?? 1).subscribe(
                res => {
                    store.address = res.body;

                    if (store.address) {
                        this.loadVillage(store.address);
                    }
                },
                error => window.console.log('Error: ', error)
            );
        });
    }

    loadVillage(address: IAddress): void {
        this.villageService.find(address.village?.id ?? 1).subscribe(
            res => {
                address.village = res.body;

                if (address.village) {
                    this.loadDistrict(address.village);
                }
            },
            error => window.console.log('Error: ', error)
        );
    }

    loadDistrict(village: IVillage): void {
        this.districtService.find(village.district?.id ?? 1).subscribe(
            res => {
                village.district = res.body;

                if (village.district) {
                    this.loadProvince(village.district);
                }
            },
            error => window.console.log('Error: ', error)
        );
    }

    loadProvince(district: IDistrict): void {
        this.provinceService.find(district.province?.id ?? 1).subscribe(
            res => {
                district.province = res.body;
            },
            error => window.console.log('Error: ', error)
        );
    }
}
