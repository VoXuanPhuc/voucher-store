import { FormArray, FormGroup } from '@angular/forms';
import { EntityResponseType } from './../../entities/voucher-status/service/voucher-status.service';
import { IVoucher } from './../../entities/voucher/voucher.model';
import { Component, OnInit } from '@angular/core';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { EventService } from 'app/entities/event/service/event.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { HttpResponse } from '@angular/common/http';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';

@Component({
  selector: 'jhi-sell-voucher',
  templateUrl: './sell-voucher.component.html',
  styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
  vouchers!: IVoucher[];
  isVoucherLoading = false;

  provinces!: number[];

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

  loadVoucherByTypeId(id: number): void {
    this.voucherService.findByTypeId(id).subscribe(
      res => {
        this.isVoucherLoading = true;
        this.vouchers = res.body ?? [];

        this.loadImageAndStoreForVoucher();
      },
      () => window.console.log('An error occurs when loading voucher data!')
    );
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
  }

  areChangedHandler(form: Array<number>): void {
    // this.provinces = new Array(form.length);
    // window.console.log("1Formmmmmmmmmmmmmmmmm: ", form);
    // const item = form.at(0);
    // for (let i = 0; i < form.length; i++) {
    //     // window.console.log("hahahhaha", form.at(i));
    //     const element = form.at(i);
    //     if (element.valid) {
    //         this.provinces.push(element.value);
    //         window.console.log(element.value, "hihi ");
    //     }
    // }
  }
}
