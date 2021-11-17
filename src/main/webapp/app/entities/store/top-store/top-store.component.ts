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

    this.getAllStore();
  }

  public async getAllStore(): Promise<void> {
    const promise = await this.storeService.query().toPromise();
    this.stores = promise.body ?? [];

    this.setAddressDetail(this.stores);
  }

  public async getAddressById(id: number): Promise<EntityResponseType> {
    const promise = await this.addressService.find(id).toPromise();

    return promise;
  }

  public setAddressDetail(stores: IStore[]): void {
    stores.forEach(store => {
      const promise = this.getAddressById(store.address?.id ?? 1);

      promise.then(data => {
        const address = data.body;
        store.address = address;
      });
    });
  }

  trackId(index: number, item: IStore): number {
    return item.id!;
  }

  ngOnInit(): void {
    this.loadAll();
  }
}
