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
        window.console.log('Tesssssssssssssssss ', this.getAddressById(1));
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  public getAddressById(id: number): IAddress {
    let address!: IAddress;

    this.addressService.find(id).subscribe((res: HttpResponse<IAddress>) => {
      address = res.body!;
    });

    return address;
  }

  trackId(index: number, item: IStore): number {
    return item.id!;
  }

  ngOnInit(): void {
    this.loadAll();
  }
}
