import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';

@Component({
  selector: 'jhi-sell-voucher-item',
  templateUrl: './sell-voucher.component.html',
  styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
  vouchers?: IVoucher[];
  isLoading = false;

  constructor(private voucherService: VoucherService, private voucherImageService: VoucherImageService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.voucherService.query().subscribe(
      res => {
        this.isLoading = true;
        this.vouchers = res.body ?? [];
        window.console.log(this.vouchers);

        this.vouchers.forEach(voucher => {
          this.voucherImageService.queryByVoucherId(voucher.id ?? 0).subscribe((resImage: HttpResponse<IVoucherImage[]>) => {
            voucher.voucherImages = resImage.body ?? [];
          }),
            () => window.alert('An error occurs when loading image data!');
        });
      },
      () => window.alert('An error occurs when loading voucher data!')
    );
  }

  // loadImage(): void {
  //     this.vouchers?.forEach(voucher => {
  //         this.voucherImageService.queryByVoucherId(voucher.id ?? 0).subscribe(
  //             (res) => {
  //                 voucher.voucherImages = res.body ?? []
  //                 window.console.log("Imagesssssssssssssssssssssssss", voucher);
  //             }
  //         ),
  //             () => window.alert("An error occurs when loading image data!")
  //     })
  // }
}
