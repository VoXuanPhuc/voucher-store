import { EntityResponseType } from './../../relationship-type/service/relationship-type.service';
import { IAddress } from 'app/entities/address/address.model';
import { IStore } from './../store.model';
import { StoreService } from './../service/store.service';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { AddressService } from 'app/entities/address/service/address.service';

@Component({
  selector: 'jhi-top-store',
  templateUrl: './top-store.component.html',
  styleUrls: ['./top-store.component.scss'],
})
export class TopStoreComponent implements OnInit {
  stores?: IStore[];
  isLoading = false;

  constructor(private storeService: StoreService, private addressService: AddressService) {}

  public loadAll(): void {
    this.isLoading = true;

    this.storeService.query().subscribe(
      (res: HttpResponse<IStore[]>) => {
        this.isLoading = false;
        this.stores = res.body ?? [];

        // window.console.log("1. Duonggggggggggggggggggggg", this.stores);

        this.setAddressDetail(this.stores);
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  public getAddressById(id: number): IAddress | null {
    let address!: IAddress;

    this.addressService.find(id).subscribe((res: EntityResponseType) => {
      address = res.body!;

      return address;
    });

    return null;
  }

  public setAddressDetail(stores: IStore[]): void {
    stores.forEach(store => {
      // window.console.log("Duonggggggggggggggggggggg", store.address?.id);
      const address: IAddress | null = this.getAddressById(store.address?.id ?? 1);
      store.address = address;
      window.console.log('Address: ', address);
    });

    // window.console.log("111111111111Duonggggggggggggggggggggg", stores);
  }

  trackId(index: number, item: IStore): number {
    return item.id!;
  }

  ngOnInit(): void {
    this.loadAll();
  }
}
