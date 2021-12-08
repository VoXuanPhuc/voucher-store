import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EventService } from 'app/entities/event/service/event.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { IVoucherImage, VoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { VoucherService } from '../service/voucher.service';
import { IVoucher, Voucher } from '../voucher.model';

@Component({
  selector: 'jhi-hot-voucher',
  templateUrl: './hot-voucher.component.html',
  styleUrls: ['./hot-voucher.component.scss'],
})
export class HotVoucherComponent implements OnInit {
  vouchers?: IVoucher[];
  isLoading = false;
  voucherImages?: IVoucherImage[];

  constructor(
    protected voucherService: VoucherService,
    protected voucherImageService: VoucherImageService,
    protected storeService: StoreService,
    protected eventService: EventService
  ) {}

  ngOnInit(): void {
    this.loadHotVoucher();
  }

  loadHotVoucher(): void {
    this.isLoading = true;
    this.voucherService.query().subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        this.isLoading = false;
        this.vouchers = res.body ?? [];

        this.vouchers.forEach(voucher => {
          this.voucherImageService.queryByVoucherId(voucher.id ?? 1).subscribe((resImages: HttpResponse<IVoucherImage[]>) => {
            voucher.voucherImages = resImages.body ?? [];
          }),
            () => {
              alert('Error');
            };
          this.eventService.find(voucher.event?.id ?? 1).subscribe(resSer => {
            voucher.event = resSer.body;
            this.storeService.find(voucher.event?.store?.id ?? 5).subscribe(resStore => {
              voucher.event!.store = resStore.body;
            });
          });
        });
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  trackId(index: number, item: IVoucher): number {
    return item.id!;
  }
}
