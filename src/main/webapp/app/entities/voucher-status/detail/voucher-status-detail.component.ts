import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherStatus } from '../voucher-status.model';

@Component({
  selector: 'jhi-voucher-status-detail',
  templateUrl: './voucher-status-detail.component.html',
})
export class VoucherStatusDetailComponent implements OnInit {
  voucherStatus: IVoucherStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherStatus }) => {
      this.voucherStatus = voucherStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
