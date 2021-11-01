import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoucherImage } from '../voucher-image.model';

@Component({
  selector: 'jhi-voucher-image-detail',
  templateUrl: './voucher-image-detail.component.html',
})
export class VoucherImageDetailComponent implements OnInit {
  voucherImage: IVoucherImage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucherImage }) => {
      this.voucherImage = voucherImage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
