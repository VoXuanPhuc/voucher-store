import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { VoucherService } from '../service/voucher.service';
import { IVoucher } from '../voucher.model';

@Component({
  selector: 'jhi-hot-voucher',
  templateUrl: './hot-voucher.component.html',
  styleUrls: ['./hot-voucher.component.scss'],
})
export class HotVoucherComponent implements OnInit {
  vouchers?: IVoucher[];
  isLoading = false;

  constructor(protected voucherService: VoucherService) {}

  ngOnInit(): void {
    this.loadHotVoucher();
  }

  loadHotVoucher(): void {
    this.isLoading = true;

    this.voucherService.query().subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        this.isLoading = false;
        this.vouchers = res.body ?? [];
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
