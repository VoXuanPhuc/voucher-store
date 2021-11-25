import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { EventService } from 'app/entities/event/service/event.service';
import { VoucherImageService } from 'app/entities/voucher-image/service/voucher-image.service';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { EntityResponseType } from './../../province/service/province.service';
import { StoreService } from './../../store/service/store.service';
import { IVoucher } from './../voucher.model';

@Component({
  selector: 'jhi-sell-voucher-item',
  templateUrl: './sell-voucher.component.html',
  styleUrls: ['./sell-voucher.component.scss'],
})
export class SellVoucherComponent implements OnInit {
  @Input() vouchers?: IVoucher[];
  @Input() isLoading = false;

  constructor(
    private voucherService: VoucherService,
    private voucherImageService: VoucherImageService,
    private eventService: EventService,
    private storeService: StoreService
  ) {}

  ngOnInit(): void {
    return;
  }

  // loadAll(): void {
  //     this.voucherService.query().subscribe(
  //         (res) => {
  //             this.isLoading = true;
  //             this.vouchers = res.body ?? [];

  //             this.vouchers.forEach(voucher => {
  //                 this.voucherImageService.queryByVoucherId(voucher.id ?? 0).subscribe(
  //                     (imageRes: HttpResponse<IVoucherImage[]>) => {
  //                         voucher.voucherImages = imageRes.body ?? [];
  //                     },
  //                     () => window.console.log('An error occurs when loading image data!')
  //                 );

  //                 this.eventService.find(voucher.event?.id ?? 0).subscribe(
  //                     (eventRes: EntityResponseType) => {
  //                         voucher.event = eventRes.body;

  //                         this.storeService.find(voucher.event?.store?.id ?? 1).subscribe(
  //                             (storeRes: EntityResponseType) => {
  //                                 voucher.event!.store = storeRes.body;
  //                             },
  //                             () => window.console.log('An error occurs when loading store data!')
  //                         );
  //                     },
  //                     () => window.console.log('An error occurs when loading event data!')
  //                 );

  //             });
  //         },
  //         () => window.console.log('An error occurs when loading voucher data!')
  //     );
  // }
}
