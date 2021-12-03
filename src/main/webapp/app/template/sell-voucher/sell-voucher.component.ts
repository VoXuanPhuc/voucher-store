import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EventService } from 'app/entities/event/service/event.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { EntityResponseType } from './../../entities/voucher-status/service/voucher-status.service';
import { PriceRange } from './model';

@Component({
  selector: 'jhi-sell-voucher',
  templateUrl: './sell-voucher.component.html',
  styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
  vouchers!: IVoucher[];
  backupVouchers!: IVoucher[];

  isVoucherLoading = false;

  typeId?: number | null;

  provinceIds = new Array(0);

  ratings = new Array(0);

  priceRange?: PriceRange = new PriceRange(0, 1000000);

  constructor(
    private voucherService: VoucherService,
    private voucherImageService: VoucherImageService,
    private eventService: EventService,
    private storeService: StoreService
  ) {}

  ngOnInit(): void {
    this.loadAllVouchers();
  }

  loadAllVouchers(): void {
    this.voucherService.query().subscribe(
      res => {
        this.isVoucherLoading = true;
        this.vouchers = res.body ?? [];

        this.loadImageAndStoreForVoucher();
      },
      () => window.console.log('An error occurs when loading voucher data!')
    );
  }

  loadVoucherByTypeIdByApi(id: number): void {
    this.typeId = id;
    this.voucherService.findByTypeId(id).subscribe(
      res => {
        this.isVoucherLoading = true;
        this.vouchers = res.body ?? [];

        this.loadImageAndStoreForVoucher();
      },
      () => window.console.log('An error occurs when loading voucher data!')
    );
  }

  loadVoucherByTypeId(): void {
    this.vouchers = this.backupVouchers.filter((voucher: IVoucher) => voucher.type?.id === this.typeId);
  }

  loadVoucherByPriceRange(): void {
    this.vouchers = this.vouchers.filter(
      (voucher: IVoucher) =>
        voucher.price! >= (this.priceRange?.minPrice ?? 0) * 1000 && voucher.price! <= (this.priceRange?.maxPrice ?? 10000000) * 1000
    );
  }

  loadVoucherByMutipleCriteria(): void {
    if (typeof this.typeId === 'number') {
      this.loadVoucherByTypeId();
    }
    this.loadVoucherByPriceRange();
  }

  loadImageAndStoreForVoucher(): void {
    this.vouchers.forEach(voucher => {
      this.voucherImageService.queryByVoucherId(voucher.id ?? 0).subscribe(
        (imageRes: HttpResponse<IVoucherImage[]>) => {
          voucher.voucherImages = imageRes.body ?? [];
        },
        () => window.console.log('An error occurs when loading image data!')
      );

      this.eventService.find(voucher.event?.id ?? 0).subscribe(
        (eventRes: EntityResponseType) => {
          voucher.event = eventRes.body;
          this.storeService.find(voucher.event?.store?.id ?? 1).subscribe(
            (storeRes: EntityResponseType) => {
              voucher.event!.store = storeRes.body;
            },
            () => window.console.log('An error occurs when loading store data!')
          );
        },
        () => window.console.log('An error occurs when loading event data!')
      );
    });
    this.backupVouchers = this.vouchers;
  }

  serviceTypeChangedHandler(id: number): void {
    this.typeId = id;
    this.loadVoucherByMutipleCriteria();
  }

  areaChangedHandler(ids: Array<number>): void {
    this.provinceIds = [];
    ids.forEach(id => this.provinceIds.push(id));
  }

  priceRangeChangedHandler(data: PriceRange): void {
    this.priceRange = new PriceRange(data.minPrice, data.maxPrice);
    this.loadVoucherByMutipleCriteria();
  }

  ratingChangedHandler(items: Array<number>): void {
    this.ratings = [];
    items.forEach(value => this.ratings.push(value));
  }
}
