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
  constructor(protected voucherService: VoucherService) {}

  ngOnInit(): void {
    this.loadHotVoucher();
  }

  loadHotVoucher(): void {
    this.voucherService.query().subscribe((res: HttpResponse<IVoucher[]>) => {
      this.vouchers = res.body ?? [];
    });
  }
}
