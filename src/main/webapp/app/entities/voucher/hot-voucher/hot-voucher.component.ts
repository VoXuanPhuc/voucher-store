import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
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

  constructor(protected voucherService: VoucherService, protected voucherImageService: VoucherImageService) {}

  ngOnInit(): void {
    this.loadHotVoucher();
  }

  loadHotVoucher(): void {
    this.isLoading = true;
    this.voucherService.query().subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        this.isLoading = false;
        this.vouchers = res.body ?? [];

        this.vouchers.forEach(item => {
          this.voucherImageService.find(1).subscribe((resImage: HttpResponse<IVoucherImage>) => {});
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
