import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherCode } from '../voucher-code.model';

@Component({
  selector: 'jhi-voucher-code-detail',
  templateUrl: './voucher-code-detail.component.html',
})
export class VoucherCodeDetailComponent implements OnInit {
  voucherCode: IVoucherCode | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherCode }) => {
      this.voucherCode = voucherCode;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
